/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC
 * LICENSE ("AGREEMENT"). [Eclipse Public License - v 1.0]
 * 
 * ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM CONSTITUTES
 * RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 * 
 * You may obtain a copy of the License at
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.sequencediagram.teoz;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.real.Real;
import net.sourceforge.plantuml.sequencediagram.AbstractMessage;
import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.sequencediagram.Note;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Context2D;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class CommunicationTileNoteBottom extends AbstractTile implements TileWithUpdateStairs, TileWithCallbackY {

	private final TileWithUpdateStairs tile;
	private final AbstractMessage message;
	private final Rose skin;
	private final ISkinParam skinParam;
	private final Note noteOnMessage;

	public Event getEvent() {
		return message;
	}

	@Override
	public double getYPoint(StringBounder stringBounder) {
		return tile.getYPoint(stringBounder);
	}

	public CommunicationTileNoteBottom(TileWithUpdateStairs tile, AbstractMessage message, Rose skin,
			ISkinParam skinParam, Note noteOnMessage) {
		this.tile = tile;
		this.message = message;
		this.skin = skin;
		this.skinParam = skinParam;
		this.noteOnMessage = noteOnMessage;
	}

	public void updateStairs(StringBounder stringBounder, double y) {
		tile.updateStairs(stringBounder, y);
	}

	private Component getComponent(StringBounder stringBounder) {
		final Component comp = skin.createComponent(noteOnMessage.getUsedStyles(), ComponentType.NOTE, null,
				noteOnMessage.getSkinParamBackcolored(skinParam), noteOnMessage.getStrings());
		return comp;
	}

	private Real getNotePosition(StringBounder stringBounder) {
		final Real minX = tile.getMinX(stringBounder);
		return minX;
	}

	public void drawU(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();
		final Component comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		final Area area = new Area(dim.getWidth(), dim.getHeight());
		tile.drawU(ug);

		final double middleMsg = (tile.getMinX(stringBounder).getCurrentValue() + tile.getMaxX(stringBounder).getCurrentValue()) / 2;

		final double xNote = getNotePosition(stringBounder).getCurrentValue();
		final double yNote = tile.getPreferredHeight(stringBounder);

		comp.drawU(ug.apply(new UTranslate(xNote, yNote + spacey)), area, (Context2D) ug);

		drawLine(ug, middleMsg, tile.getYPoint(stringBounder), xNote + dim.getWidth() / 2, yNote + spacey
				+ Rose.paddingY);

	}

	private final double spacey = 10;

	private void drawLine(UGraphic ug, double x1, double y1, double x2, double y2) {
		final HColor color = new Rose().getHtmlColor(skinParam, ColorParam.arrow);

		final double dx = x2 - x1;
		final double dy = y2 - y1;

		ug.apply(new UTranslate(x1, y1)).apply(color).apply(new UStroke(2, 2, 1))
				.draw(new ULine(dx, dy));

	}

	public double getPreferredHeight(StringBounder stringBounder) {
		final Component comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		return tile.getPreferredHeight(stringBounder) + dim.getHeight() + spacey;
	}

	public void addConstraints(StringBounder stringBounder) {
		tile.addConstraints(stringBounder);
	}

	public Real getMinX(StringBounder stringBounder) {
		return tile.getMinX(stringBounder);
	}

	public Real getMaxX(StringBounder stringBounder) {
		return tile.getMaxX(stringBounder);
	}

	public void callbackY(double y) {
		if (tile instanceof TileWithCallbackY) {
			((TileWithCallbackY) tile).callbackY(y);
		}
	}

}
