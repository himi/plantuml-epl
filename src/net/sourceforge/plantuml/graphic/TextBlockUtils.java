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
package net.sourceforge.plantuml.graphic;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.CornerParam;
import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.LineParam;
import net.sourceforge.plantuml.SkinParam;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.posimo.Positionable;
import net.sourceforge.plantuml.posimo.PositionableImpl;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.style.ClockwiseTopRightBottomLeft;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.LimitFinder;
import net.sourceforge.plantuml.ugraphic.MinMax;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UImage;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;

public class TextBlockUtils {

	public static final TextBlock EMPTY_TEXT_BLOCK = TextBlockUtils.empty(0, 0);

	public static TextBlock bordered(TextBlock textBlock, UStroke stroke, HColor borderColor, HColor backgroundColor,
			double cornersize) {
		return new TextBlockBordered(textBlock, stroke, borderColor, backgroundColor, cornersize);
	}

	public static TextBlock bordered(TextBlock textBlock, UStroke stroke, HColor borderColor, HColor backgroundColor,
			double cornersize, double marginX, double marginY) {
		return new TextBlockBordered(textBlock, stroke, borderColor, backgroundColor, cornersize, marginX, marginY);
	}

	public static TextBlock bordered(TextBlock textBlock, UStroke stroke, HColor borderColor, HColor backgroundColor,
			double cornersize, ClockwiseTopRightBottomLeft margins) {
		return new TextBlockBordered(textBlock, stroke, borderColor, backgroundColor, cornersize, margins);
	}

	public static TextBlock title(FontConfiguration font, Display stringsToDisplay, ISkinParam skinParam) {
		if (SkinParam.USE_STYLES()) {
			throw new UnsupportedOperationException();
		}
		UStroke stroke = skinParam.getThickness(LineParam.titleBorder, null);
		final Rose rose = new Rose();
		HColor borderColor = rose.getHtmlColor(skinParam, ColorParam.titleBorder);
		final HColor backgroundColor = rose.getHtmlColor(skinParam, ColorParam.titleBackground);
		final TextBlockTitle result = new TextBlockTitle(font, stringsToDisplay, skinParam);
		if (stroke == null && borderColor == null) {
			return result;
		}
		if (stroke == null) {
			stroke = new UStroke(1.5);
		}
		if (borderColor == null) {
			borderColor = HColorUtils.BLACK;
		}
		final double corner = skinParam.getRoundCorner(CornerParam.titleBorder, null);
		return withMargin(bordered(result, stroke, borderColor, backgroundColor, corner), 2, 2);
	}

	public static TextBlock withMargin(TextBlock textBlock, double marginX, double marginY) {
		return new TextBlockMarged(textBlock, marginY, marginX, marginY, marginX);
	}

	public static TextBlock withMargin(TextBlock textBlock, ClockwiseTopRightBottomLeft margins) {
		return new TextBlockMarged(textBlock, margins);
	}

	public static TextBlock withMargin(TextBlock textBlock, double marginX1, double marginX2, double marginY1,
			double marginY2) {
		return new TextBlockMarged(textBlock, marginY1, marginX2, marginY2, marginX1);
	}

	public static TextBlock withMinWidth(TextBlock textBlock, double minWidth,
			HorizontalAlignment horizontalAlignment) {
		return new TextBlockMinWidth(textBlock, minWidth, horizontalAlignment);
	}

	public static TextBlock empty(final double width, final double height) {
		return new AbstractTextBlock() {
			public void drawU(UGraphic ug) {
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return new Dimension2DDouble(width, height);
			}
		};
	}

	public static Positionable asPositionable(TextBlock textBlock, StringBounder stringBounder, Point2D pt) {
		return new PositionableImpl(pt, textBlock.calculateDimension(stringBounder));
	}

	public static Positionable asPositionable(Dimension2D dim, StringBounder stringBounder, Point2D pt) {
		return new PositionableImpl(pt, dim);
	}

	public static TextBlock mergeLR(TextBlock b1, TextBlock b2, VerticalAlignment verticallAlignment) {
		if (b1 == EMPTY_TEXT_BLOCK) {
			return b2;
		}
		if (b2 == EMPTY_TEXT_BLOCK) {
			return b1;
		}
		return new TextBlockHorizontal(b1, b2, verticallAlignment);
	}

	public static TextBlock mergeTB(TextBlock b1, TextBlock b2, HorizontalAlignment horizontalAlignment) {
		if (b1 == EMPTY_TEXT_BLOCK) {
			return b2;
		}
		if (b2 == EMPTY_TEXT_BLOCK) {
			return b1;
		}
		return new TextBlockVertical2(b1, b2, horizontalAlignment);
	}

	// public static TextBlockBackcolored mergeColoredTB(TextBlockBackcolored b1,
	// TextBlockBackcolored b2,
	// HorizontalAlignment horizontalAlignment) {
	// return addBackcolor(mergeTB(b1, b2, horizontalAlignment), b1.getBackcolor());
	// }

	public static MinMax getMinMax(UDrawable tb, StringBounder stringBounder, boolean initToZero) {
		final LimitFinder limitFinder = new LimitFinder(stringBounder, initToZero);
		tb.drawU(limitFinder);
		return limitFinder.getMinMax();
	}

	public static boolean isEmpty(TextBlock text, StringBounder dummyStringBounder) {
		if (text == null) {
			return true;
		}
		final Dimension2D dim = text.calculateDimension(dummyStringBounder);
		return dim.getHeight() == 0 && dim.getWidth() == 0;
	}

	public static FontRenderContext getFontRenderContext() {
		return FileFormat.gg.getFontRenderContext();
	}

	public static LineMetrics getLineMetrics(UFont font, String text) {
		return font.getLineMetrics(FileFormat.gg, text);
	}

	public static FontMetrics getFontMetrics(Font font) {
		return FileFormat.gg.getFontMetrics(font);
	}

	public static TextBlock fullInnerPosition(final TextBlock bloc, final String display) {
		return new TextBlock() {

			public void drawU(UGraphic ug) {
				bloc.drawU(ug);
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return bloc.calculateDimension(stringBounder);
			}

			public MinMax getMinMax(StringBounder stringBounder) {
				return bloc.getMinMax(stringBounder);
			}

			public Rectangle2D getInnerPosition(String member, StringBounder stringBounder, InnerStrategy strategy) {
				if (strategy.check(display, member)) {
					final Dimension2D dim = calculateDimension(stringBounder);
					return new Rectangle2D.Double(0, 0, dim.getWidth(), dim.getHeight());
				}
				return null;
			}

		};
	}

	public static TextBlockBackcolored addBackcolor(final TextBlock text, final HColor backColor) {
		return new TextBlockBackcolored() {
			public void drawU(UGraphic ug) {
				text.drawU(ug);
			}

			public MinMax getMinMax(StringBounder stringBounder) {
				return text.getMinMax(stringBounder);
			}

			public Rectangle2D getInnerPosition(String member, StringBounder stringBounder, InnerStrategy strategy) {
				return text.getInnerPosition(member, stringBounder, strategy);
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return text.calculateDimension(stringBounder);
			}

			public HColor getBackcolor() {
				return backColor;
			}
		};
	}

	public static TextBlock fromUImage(final UImage image) {
		return new TextBlock() {

			public void drawU(UGraphic ug) {
				ug.draw(image);
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return new Dimension2DDouble(image.getWidth(), image.getHeight());
			}

			public MinMax getMinMax(StringBounder stringBounder) {
				return MinMax.fromMax(image.getWidth(), image.getHeight());
			}

			public Rectangle2D getInnerPosition(String member, StringBounder stringBounder, InnerStrategy strategy) {
				return null;
			}

		};
	}

}
