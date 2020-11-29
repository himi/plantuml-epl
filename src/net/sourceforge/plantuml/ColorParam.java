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
package net.sourceforge.plantuml;

import net.sourceforge.plantuml.graphic.color.ColorType;
import net.sourceforge.plantuml.ugraphic.color.HColor;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;


public enum ColorParam {
	background(HColorUtils.WHITE, true, ColorType.BACK),
	hyperlink(HColorUtils.BLUE),
	
	activityDiamondBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	activityDiamondBorder(HColorUtils.MY_RED, ColorType.LINE),
	activityBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	activityBorder(HColorUtils.MY_RED, ColorType.LINE),
	activityStart(HColorUtils.BLACK),
	activityEnd(HColorUtils.BLACK),
	activityBar(HColorUtils.BLACK),
	swimlaneBorder(HColorUtils.BLACK),
	swimlaneTitleBackground(null),
	
	usecaseBorder(HColorUtils.MY_RED, ColorType.LINE),
	usecaseBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),

	objectBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	objectBorder(HColorUtils.MY_RED, ColorType.LINE),
	
	classHeaderBackground(null, true, ColorType.BACK),
	classBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	enumBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	classBorder(HColorUtils.MY_RED, ColorType.LINE),
	stereotypeCBackground(HColorUtils.COL_ADD1B2),
	stereotypeUBackground(HColorUtils.COL_B6DBBB),
	stereotypeNBackground(HColorUtils.COL_E3664A),
	stereotypeABackground(HColorUtils.COL_A9DCDF),
	stereotypeIBackground(HColorUtils.COL_B4A7E5),
	stereotypeEBackground(HColorUtils.COL_EB937F),
	stereotypeCBorder(null),
	stereotypeUBorder(null),
	stereotypeNBorder(null),
	stereotypeABorder(null),
	stereotypeIBorder(null),
	stereotypeEBorder(null),

	recHeaderBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
		
	packageBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	packageBorder(HColorUtils.BLACK, ColorType.LINE),

	partitionBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	partitionBorder(HColorUtils.BLACK, ColorType.LINE),

	componentBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	componentBorder(HColorUtils.MY_RED, ColorType.LINE),
	interfaceBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	interfaceBorder(HColorUtils.MY_RED, ColorType.LINE),
	arrow(HColorUtils.MY_RED, ColorType.ARROW),
	arrowHead(HColorUtils.MY_RED, null),

	stateBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	stateBorder(HColorUtils.MY_RED, ColorType.LINE),
	stateStart(HColorUtils.BLACK),
	stateEnd(HColorUtils.BLACK),

	noteBackground(HColorUtils.COL_FBFB77, true, ColorType.BACK),
	noteBorder(HColorUtils.MY_RED, ColorType.LINE),
	
	legendBackground(HColorUtils.COL_DDDDDD, true, ColorType.BACK),
	legendBorder(HColorUtils.BLACK, ColorType.LINE),
	
	titleBackground(null, true, ColorType.BACK),
	titleBorder(null, ColorType.LINE),

	diagramBorder(null, ColorType.LINE),
	
	actorBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	actorBorder(HColorUtils.MY_RED, ColorType.LINE),
	participantBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	participantBorder(HColorUtils.MY_RED, ColorType.LINE),
	sequenceGroupBorder(HColorUtils.BLACK, ColorType.LINE),
	sequenceGroupBackground(HColorUtils.COL_EEEEEE, true, ColorType.BACK),
	sequenceGroupBodyBackground(HColorUtils.RED, true, ColorType.BACK),
	sequenceReferenceBorder(HColorUtils.BLACK, ColorType.LINE),
	sequenceReferenceHeaderBackground(HColorUtils.COL_EEEEEE, true, ColorType.BACK),
	sequenceReferenceBackground(HColorUtils.WHITE, true, ColorType.BACK),
	sequenceDividerBackground(HColorUtils.COL_EEEEEE, true, ColorType.BACK),
	sequenceDividerBorder(HColorUtils.BLACK, ColorType.LINE),
	sequenceLifeLineBackground(HColorUtils.WHITE, true, ColorType.BACK),
	sequenceLifeLineBorder(HColorUtils.MY_RED, ColorType.LINE),
	sequenceNewpageSeparator(HColorUtils.BLACK, ColorType.LINE),
	sequenceBoxBorder(HColorUtils.MY_RED, ColorType.LINE),
	sequenceBoxBackground(HColorUtils.COL_DDDDDD, true, ColorType.BACK),
	
	artifactBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	artifactBorder(HColorUtils.BLACK, ColorType.LINE),
	cloudBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	cloudBorder(HColorUtils.BLACK, ColorType.LINE),
	queueBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	queueBorder(HColorUtils.MY_RED, ColorType.LINE),
	stackBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	stackBorder(HColorUtils.MY_RED, ColorType.LINE),
	databaseBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	databaseBorder(HColorUtils.BLACK, ColorType.LINE),
	folderBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	folderBorder(HColorUtils.BLACK, ColorType.LINE),
	fileBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	fileBorder(HColorUtils.BLACK, ColorType.LINE),
	frameBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	frameBorder(HColorUtils.BLACK, ColorType.LINE),
	nodeBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	nodeBorder(HColorUtils.BLACK, ColorType.LINE),
	rectangleBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	rectangleBorder(HColorUtils.BLACK, ColorType.LINE),
	archimateBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	archimateBorder(HColorUtils.BLACK, ColorType.LINE),
	cardBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	cardBorder(HColorUtils.BLACK, ColorType.LINE),
	agentBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	agentBorder(HColorUtils.MY_RED, ColorType.LINE),
	storageBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	storageBorder(HColorUtils.BLACK, ColorType.LINE),
	boundaryBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	boundaryBorder(HColorUtils.MY_RED, ColorType.LINE),
	collectionsBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	collectionsBorder(HColorUtils.MY_RED, ColorType.LINE),
	controlBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	controlBorder(HColorUtils.MY_RED, ColorType.LINE),
	entityBackground(HColorUtils.MY_YELLOW, true, ColorType.BACK),
	entityBorder(HColorUtils.MY_RED, ColorType.LINE),

	
	iconPrivate(HColorUtils.COL_C82930),
	iconPrivateBackground(HColorUtils.COL_F24D5C),
	iconPackage(HColorUtils.COL_1963A0),
	iconPackageBackground(HColorUtils.COL_4177AF),
	iconProtected(HColorUtils.COL_B38D22),
	iconProtectedBackground(HColorUtils.COL_FFFF44),
	iconPublic(HColorUtils.COL_038048),
	iconPublicBackground(HColorUtils.COL_84BE84),
	iconIEMandatory(HColorUtils.BLACK),
	iconRedefined(HColorUtils.BLACK),
	iconRedefinedBackground(HColorUtils.COL_FFFF44),
	
	arrowLollipop(HColorUtils.WHITE),
	
	machineBackground(HColorUtils.WHITE), 
	machineBorder(HColorUtils.BLACK, ColorType.LINE),
	requirementBackground(HColorUtils.WHITE), 
	requirementBorder(HColorUtils.BLACK, ColorType.LINE),
	designedBackground(HColorUtils.WHITE), 
	designedBorder(HColorUtils.BLACK, ColorType.LINE),
	domainBackground(HColorUtils.WHITE), 
	domainBorder(HColorUtils.BLACK, ColorType.LINE),
	lexicalBackground(HColorUtils.WHITE), 
	lexicalBorder(HColorUtils.BLACK, ColorType.LINE),
	biddableBackground(HColorUtils.WHITE), 
	biddableBorder(HColorUtils.BLACK, ColorType.LINE);

	private final boolean isBackground;
	private final HColor defaultValue;
	private final ColorType colorType;
	
	private ColorParam(HColor defaultValue, ColorType colorType) {
		this(defaultValue, false, colorType);
	}
	
	private ColorParam(HColor defaultValue) {
		this(defaultValue, false, null);
	}
	
	private ColorParam() {
		this(null, false, null);
	}
	
	private ColorParam(boolean isBackground) {
		this(null, isBackground, null);
	}
	
	private ColorParam(HColor defaultValue, boolean isBackground, ColorType colorType) {
		this.isBackground = isBackground;
		this.defaultValue = defaultValue;
		this.colorType = colorType;
		if (colorType == ColorType.BACK && isBackground == false) {
			System.err.println(this);
			throw new IllegalStateException();
		}
	}

	protected boolean isBackground() {
		return isBackground;
	}

	public final HColor getDefaultValue() {
		return defaultValue;
	}

	public ColorType getColorType() {
		return colorType;
	}
}
