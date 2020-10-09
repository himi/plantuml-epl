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
package net.sourceforge.plantuml.skin;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.graphic.AbstractTextBlock;
import net.sourceforge.plantuml.graphic.InnerStrategy;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorNone;

public enum VisibilityModifier {
	PRIVATE_FIELD(ColorParam.iconPrivate, null), PROTECTED_FIELD(ColorParam.iconProtected, null),
	PACKAGE_PRIVATE_FIELD(ColorParam.iconPackage, null), PUBLIC_FIELD(ColorParam.iconPublic, null),

	PRIVATE_METHOD(ColorParam.iconPrivate, ColorParam.iconPrivateBackground),
	PROTECTED_METHOD(ColorParam.iconProtected, ColorParam.iconProtectedBackground),
	PACKAGE_PRIVATE_METHOD(ColorParam.iconPackage, ColorParam.iconPackageBackground),
	PUBLIC_METHOD(ColorParam.iconPublic, ColorParam.iconPublicBackground),

	IE_MANDATORY(ColorParam.iconIEMandatory, ColorParam.iconIEMandatory),
    REDEFINED(ColorParam.iconRedefined, ColorParam.iconRedefinedBackground);

	private final ColorParam foregroundParam;
	private final ColorParam backgroundParam;

	public static String regexForVisibilityCharacterInClassName() {
		return "[-#+~\\>]";
	}

	private VisibilityModifier(ColorParam foreground, ColorParam background) {
		this.foregroundParam = foreground;
		this.backgroundParam = background;
	}

	public UDrawable getUDrawable(final int size, final HColor foregroundColor, final HColor backgoundColor) {
		return new UDrawable() {
			public void drawU(UGraphic ug) {
				drawInternal(ug, size, foregroundColor, backgoundColor, 0, 0);
			}

		};
	}

	public TextBlock getUBlock(final int size, final HColor foregroundColor, final HColor backgoundColor,
			final boolean withInvisibleRectanble) {
		return new AbstractTextBlock() {

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return new Dimension2DDouble(size + 1, size + 1);
			}

			@Override
			public Rectangle2D getInnerPosition(String member, StringBounder stringBounder, InnerStrategy strategy) {
				return null;
			}

			public void drawU(UGraphic ug) {
				if (withInvisibleRectanble) {
					ug.apply(new HColorNone()).draw(new URectangle(size * 2, size));
				}
				drawInternal(ug, size, foregroundColor, backgoundColor, 0, 0);
			}
		};
	}

	private void drawInternal(UGraphic ug, int size, final HColor foregroundColor, final HColor backgoundColor,
			double x, double y) {
		if (backgoundColor == null) {
			ug = ug.apply(new HColorNone().bg());
		} else {
			ug = ug.apply(backgoundColor.bg());
		}
		ug = ug.apply(foregroundColor);
		size = ensureEven(size);
		switch (this) {
		case PACKAGE_PRIVATE_FIELD:
			drawTriangle(ug, false, size, x, y);
			break;

		case PRIVATE_FIELD:
			drawSquare(ug, false, size, x, y);
			break;

		case PROTECTED_FIELD:
			drawDiamond(ug, false, size, x, y);
			break;

		case PUBLIC_FIELD:
			drawCircle(ug, false, size, x, y);
			break;

		case PACKAGE_PRIVATE_METHOD:
			drawTriangle(ug, true, size, x, y);
			break;

		case PRIVATE_METHOD:
			drawSquare(ug, true, size, x, y);
			break;

		case PROTECTED_METHOD:
			drawDiamond(ug, true, size, x, y);
			break;

		case PUBLIC_METHOD:
			drawCircle(ug, true, size, x, y);
			break;

		case IE_MANDATORY:
			drawCircle(ug, true, size, x, y);
			break;

		case REDEFINED:
			drawRightTriangle(ug, size, x, y, true);
            break;

		default:
			throw new IllegalStateException();
		}
	}

	private void drawSquare(UGraphic ug, boolean filled, int size, double x, double y) {
		ug.apply(new UTranslate(x + 2, y + 2)).draw(new URectangle(size - 4, size - 4));
	}

	private void drawCircle(UGraphic ug, boolean filled, int size, double x, double y) {
		ug.apply(new UTranslate(x + 2, y + 2)).draw(new UEllipse(size - 4, size - 4));
	}

	static private int ensureEven(int n) {
		if (n % 2 == 1) {
			n--;
		}
		return n;
	}

	private void drawDiamond(UGraphic ug, boolean filled, int size, double x, double y) {
		final UPolygon poly = new UPolygon();
		size -= 2;
		poly.addPoint(size / 2.0, 0);
		poly.addPoint(size, size / 2.0);
		poly.addPoint(size / 2.0, size);
		poly.addPoint(0, size / 2.0);
		ug.apply(new UTranslate(x + 1, y)).draw(poly);
	}

	private void drawTriangle(UGraphic ug, boolean filled, int size, double x, double y) {
		final UPolygon poly = new UPolygon();
		size -= 2;
		poly.addPoint(size / 2.0, 1);
		poly.addPoint(0, size - 1);
		poly.addPoint(size, size - 1);
		ug.apply(new UTranslate(x + 1, y)).draw(poly);
	}

	private void drawRightTriangle(UGraphic ug, int size, double x, double y, boolean withBar) {
		final UPolygon poly = new UPolygon();
		poly.addPoint(3, 1);
		poly.addPoint(size, size / 2);
		poly.addPoint(3, size - 1);
        final ULine line = new ULine(0, size - 1);
        final UTranslate pos = new UTranslate(x, y);
        ug.apply(pos).draw(line);
		ug.apply(pos).draw(poly);
	}

	public static boolean isVisibilityCharacter(CharSequence s) {
		if (s.length() <= 2) {
			return false;
		}
		final char c = s.charAt(0);
		if (s.charAt(1) == c) {
			return false;
		}
		if (c == '-') {
			return true;
		}
		if (c == '#') {
			return true;
		}
		if (c == '+') {
			return true;
		}
		if (c == '~') {
			return true;
		}
		if (c == '*') {
			return true;
		}
		if (c == '>') {
			return true;
		}
		return false;
	}

	public static VisibilityModifier getVisibilityModifier(CharSequence s, boolean isField) {
		if (s.length() <= 2) {
			return null;
		}
		final char c = s.charAt(0);
		if (s.charAt(1) == c) {
			return null;
		}
		if (isField) {
			return getVisibilityModifierForField(c);
		}
		return getVisibilityModifierForMethod(c);
	}

	private static VisibilityModifier getVisibilityModifierForField(char c) {
		if (c == '-') {
			return VisibilityModifier.PRIVATE_FIELD;
		}
		if (c == '#') {
			return VisibilityModifier.PROTECTED_FIELD;
		}
		if (c == '+') {
			return VisibilityModifier.PUBLIC_FIELD;
		}
		if (c == '~') {
			return VisibilityModifier.PACKAGE_PRIVATE_FIELD;
		}
		if (c == '*') {
			return VisibilityModifier.IE_MANDATORY;
		}
		if (c == '>') {
			return VisibilityModifier.REDEFINED;
		}
		return null;
	}

	private static VisibilityModifier getVisibilityModifierForMethod(char c) {
		if (c == '-') {
			return VisibilityModifier.PRIVATE_METHOD;
		}
		if (c == '#') {
			return VisibilityModifier.PROTECTED_METHOD;
		}
		if (c == '+') {
			return VisibilityModifier.PUBLIC_METHOD;
		}
		if (c == '~') {
			return VisibilityModifier.PACKAGE_PRIVATE_METHOD;
		}
		if (c == '*') {
			return VisibilityModifier.IE_MANDATORY;
		}
		if (c == '>') {
			return VisibilityModifier.REDEFINED;
		}
		return null;
	}

	public final ColorParam getForeground() {
		return foregroundParam;
	}

	public final ColorParam getBackground() {
		return backgroundParam;
	}

	public String getXmiVisibility() {
		if (this == PUBLIC_FIELD || this == PUBLIC_METHOD) {
			return "public";
		}
		if (this == PRIVATE_FIELD || this == PRIVATE_METHOD) {
			return "private";
		}
		if (this == PROTECTED_FIELD || this == PROTECTED_METHOD) {
			return "protected";
		}
		if (this == PACKAGE_PRIVATE_FIELD || this == VisibilityModifier.PACKAGE_PRIVATE_METHOD) {
			return "package";
		}
		throw new IllegalStateException();
	}

}
