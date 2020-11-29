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
package net.sourceforge.plantuml.svek.image;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ColorParam;
import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.Guillemet;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.SkinParam;
import net.sourceforge.plantuml.SkinParamUtils;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.EntityPortion;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.cucadiagram.PortionShower;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.CircledCharacter;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockGeneric;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.graphic.VerticalAlignment;
import net.sourceforge.plantuml.skin.VisibilityModifier;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.style.SName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.svek.AbstractEntityImage;
import net.sourceforge.plantuml.svek.HeaderLayout;
import net.sourceforge.plantuml.svek.ShapeType;
import net.sourceforge.plantuml.ugraphic.UFont;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class EntityImageClassHeader extends AbstractEntityImage {

	final private HeaderLayout headerLayout;

	public EntityImageClassHeader(IEntity entity, ISkinParam skinParam, PortionShower portionShower) {
		super(entity, skinParam);

		final boolean italic = entity.getLeafType() == LeafType.ABSTRACT_CLASS
				|| entity.getLeafType() == LeafType.INTERFACE;

		final Stereotype stereotype = entity.getStereotype();

		final String generic;
		final VisibilityModifier modifier;
		TextBlock circledCharacter = null;
		if (!entity.isGroup() && (entity instanceof ILeaf)) {
			final ILeaf leaf = (ILeaf) entity;
			modifier = leaf.getVisibilityModifier();

			if (skinParam.displayGenericWithOldFashion()) {
				generic = null; 
			} else {
				generic = leaf.getGeneric();
			}

            if (portionShower.showPortion(EntityPortion.CIRCLED_CHARACTER, leaf)) {
                circledCharacter = TextBlockUtils.withMargin(getCircledCharacter(leaf, skinParam), 4, 0, 5, 5);
            }
		} else {
			modifier = null;
			generic = null;
		}

		FontConfiguration fontConfigurationName;

		if (SkinParam.USE_STYLES()) {
			final Style style = FontParam.CLASS.getStyleDefinition(SName.classDiagram)
					.getMergedStyle(skinParam.getCurrentStyleBuilder());
			fontConfigurationName = new FontConfiguration(style, skinParam, stereotype, FontParam.CLASS);
		} else {
			fontConfigurationName = new FontConfiguration(getSkinParam(), FontParam.CLASS, stereotype);
		}
		if (italic) {
			fontConfigurationName = fontConfigurationName.italic();
		}
		Display display = entity.getDisplay();
		if (generic != null) {
			display = display.addGeneric(generic);
		}
		TextBlock name = display.createWithNiceCreoleMode(fontConfigurationName, HorizontalAlignment.CENTER, skinParam);

		if (modifier == null) {
			name = TextBlockUtils.withMargin(name, 3, 3, 0, 0);
		} else {
			final Rose rose = new Rose();
			final HColor back = rose.getHtmlColor(skinParam, modifier.getBackground());
			final HColor fore = rose.getHtmlColor(skinParam, modifier.getForeground());

			final TextBlock uBlock = modifier.getUBlock(skinParam.classAttributeIconSize(), fore, back, false);
			name = TextBlockUtils.mergeLR(uBlock, name, VerticalAlignment.CENTER);
			name = TextBlockUtils.withMargin(name, 3, 3, 0, 0);
		}

		final TextBlock stereo;
		if (stereotype == null || stereotype.getLabel(Guillemet.DOUBLE_COMPARATOR) == null
				|| portionShower.showPortion(EntityPortion.STEREOTYPE, entity) == false) {
			stereo = null;
		} else {
			stereo = TextBlockUtils.withMargin(Display.create(stereotype.getLabels(skinParam.guillemet())).create(
					new FontConfiguration(getSkinParam(), FontParam.CLASS_STEREOTYPE, stereotype),
					HorizontalAlignment.CENTER, skinParam), 1, 0);
		}

		TextBlock genericBlock;
		if (generic == null) {
			genericBlock = null;
		} else {
			genericBlock = Display.getWithNewlines(generic).create(
					new FontConfiguration(getSkinParam(), FontParam.CLASS_STEREOTYPE, stereotype),
					HorizontalAlignment.CENTER, skinParam);
			genericBlock = TextBlockUtils.withMargin(genericBlock, 1, 1);
			final HColor classBackground = SkinParamUtils.getColor(getSkinParam(), stereotype, ColorParam.background);

			final HColor classBorder = SkinParamUtils.getFontColor(getSkinParam(), FontParam.CLASS_STEREOTYPE,
					stereotype);
			genericBlock = new TextBlockGeneric(genericBlock, classBackground, classBorder);
			genericBlock = TextBlockUtils.withMargin(genericBlock, 1, 1);
		}
		this.headerLayout = new HeaderLayout(circledCharacter, stereo, name, genericBlock);
	}

	private TextBlock getCircledCharacter(ILeaf entity, ISkinParam skinParam) {
		final Stereotype stereotype = entity.getStereotype();
		if (stereotype != null && stereotype.getSprite(skinParam) != null) {
			return stereotype.getSprite(skinParam);
		}
		final UFont font = SkinParamUtils.getFont(getSkinParam(), FontParam.CIRCLED_CHARACTER, null);
		final HColor classBorder = SkinParamUtils.getColor(getSkinParam(), stereotype, ColorParam.classBorder);
		final HColor fontColor = SkinParamUtils.getFontColor(getSkinParam(), FontParam.CIRCLED_CHARACTER, null);
		if (stereotype != null && stereotype.getCharacter() != 0) {
			return new CircledCharacter(stereotype.getCharacter(), getSkinParam().getCircledCharacterRadius(), font,
					stereotype.getHtmlColor(), classBorder, fontColor);
		}
		final LeafType leafType = entity.getLeafType();
		final HColor spotBackColor = SkinParamUtils.getColor(getSkinParam(), stereotype, spotBackground(leafType));
		HColor spotBorder = SkinParamUtils.getColor(getSkinParam(), stereotype, spotBorder(leafType));
		if (spotBorder == null) {
			spotBorder = classBorder;
		}
		char circledChar = 0;
		if (stereotype != null) {
			circledChar = getSkinParam().getCircledCharacter(stereotype);
		}
		if (circledChar == 0) {
			circledChar = getCircledChar(leafType);
		}
		return new CircledCharacter(circledChar, getSkinParam().getCircledCharacterRadius(), font, spotBackColor,
				spotBorder, fontColor);
	}

	private ColorParam spotBackground(LeafType leafType) {
		switch (leafType) {
		case ANNOTATION:
			return ColorParam.stereotypeNBackground;
		case ABSTRACT_CLASS:
			return ColorParam.stereotypeABackground;
		case CLASS:
		case REC_DEF:
			return ColorParam.stereotypeCBackground;
		case USAGE:
		case REC_USAGE:
			return ColorParam.stereotypeUBackground;
		case INTERFACE:
			return ColorParam.stereotypeIBackground;
		case ENUM:
			return ColorParam.stereotypeEBackground;
		case ENTITY:
			return ColorParam.stereotypeCBackground;
		default:
			assert false;
			return null;
		}
	}

	private ColorParam spotBorder(LeafType leafType) {
		switch (leafType) {
		case ANNOTATION:
			return ColorParam.stereotypeNBorder;
		case ABSTRACT_CLASS:
			return ColorParam.stereotypeABorder;
		case CLASS:
		case REC_DEF:
			return ColorParam.stereotypeCBorder;
		case USAGE:
		case REC_USAGE:
			return ColorParam.stereotypeUBorder;
		case INTERFACE:
			return ColorParam.stereotypeIBorder;
		case ENUM:
			return ColorParam.stereotypeEBorder;
		case ENTITY:
			return ColorParam.stereotypeCBorder;
		default:
			assert false;
			return null;
		}

	}

	private char getCircledChar(LeafType leafType) {
		switch (leafType) {
		case ANNOTATION:
			return '@';
		case ABSTRACT_CLASS:
			return 'A';
		case CLASS:
			return 'C';
		case USAGE:
		case REC_USAGE:
			return 'U';
		case REC_DEF:
			return 'D';
		case INTERFACE:
			return 'I';
		case ENUM:
			return 'E';
		case ENTITY:
			return 'E';
		default:
			assert false;
			return '?';
		}
	}

	public Dimension2D calculateDimension(StringBounder stringBounder) {
		return headerLayout.getDimension(stringBounder);
	}

	final public void drawU(UGraphic ug) {
		throw new UnsupportedOperationException();
	}

	public void drawU(UGraphic ug, double width, double height) {
		headerLayout.drawU(ug, width, height);
	}

	public ShapeType getShapeType() {
		return ShapeType.RECTANGLE;
	}

}
