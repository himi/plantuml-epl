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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.real.Real;
import net.sourceforge.plantuml.real.RealUtils;
import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class TileParallel implements Tile, TileWithUpdateStairs, TileWithCallbackY {

	private final List<Tile> tiles = new ArrayList<Tile>();

	public void callbackY(double y) {
		for (Tile tile : tiles) {
			if (tile instanceof TileWithCallbackY) {
				((TileWithCallbackY) tile).callbackY(y);
			}
		}
	}

	public void updateStairs(StringBounder stringBounder, double y) {
		for (Tile tile : tiles) {
			if (tile instanceof TileWithUpdateStairs) {
				((TileWithUpdateStairs) tile).updateStairs(stringBounder, y);
			}
		}
	}

	public void add(Tile tile) {
		this.tiles.add(tile);
	}

	public void drawU(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();
		// final double totalHeight = getPreferredHeight(stringBounder);
		final double yPointAll = getYPoint(stringBounder);
		for (Tile tile : tiles) {
			final double yPoint = tile.getYPoint(stringBounder);
			// tile.drawU(ug.apply(UTranslate.dy(totalHeight -
			// tile.getPreferredHeight(stringBounder))));
			tile.drawU(ug.apply(UTranslate.dy(yPointAll - yPoint)));
		}
	}

	public double getYPoint(StringBounder stringBounder) {
		double result = 0;
		for (Tile tile : tiles) {
			result = Math.max(result, tile.getYPoint(stringBounder));
		}
		return result;
	}

	public double getZ(StringBounder stringBounder) {
		double result = 0;
		for (Tile tile : tiles) {
			result = Math.max(result, tile.getZ(stringBounder));
		}
		return result;
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		return getYPoint(stringBounder) + getZ(stringBounder);
	}

	public void addConstraints(StringBounder stringBounder) {
		for (Tile tile : tiles) {
			tile.addConstraints(stringBounder);
		}
	}

	public Real getMinX(final StringBounder stringBounder) {
		return RealUtils.min(new AbstractCollection<Real>() {
			public Iterator<Real> iterator() {
				return new Iterator<Real>() {
					private final Iterator<Tile> source = tiles.iterator();

					public boolean hasNext() {
						return source.hasNext();
					}

					public Real next() {
						return source.next().getMinX(stringBounder);
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			public int size() {
				return tiles.size();
			}
		});
	}

	public Real getMaxX(final StringBounder stringBounder) {
		return RealUtils.max(new AbstractCollection<Real>() {
			public Iterator<Real> iterator() {
				return new Iterator<Real>() {
					private final Iterator<Tile> source = tiles.iterator();

					public boolean hasNext() {
						return source.hasNext();
					}

					public Real next() {
						return source.next().getMaxX(stringBounder);
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			public int size() {
				return tiles.size();
			}
		});
	}

	public Event getEvent() {
		return null;
	}

	public boolean matchAnchorV1(String anchor) {
		for (Tile tile : tiles) {
			if (tile.matchAnchorV1(anchor)) {
				return true;
			}
		}
		return false;
	}

}
