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
 * Contributor: Hisashi Miyashita (himi@mgnite.com)
 */
package net.sourceforge.plantuml.sysml2;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.classdiagram.command.CommandAddMethod;
import net.sourceforge.plantuml.classdiagram.command.CommandHideShow2;
import net.sourceforge.plantuml.classdiagram.command.CommandNamespaceSeparator;
import net.sourceforge.plantuml.classdiagram.command.CommandUrl;
import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.command.CommandEndPackage;
import net.sourceforge.plantuml.command.CommandFootboxIgnored;
import net.sourceforge.plantuml.command.CommandPackage;
import net.sourceforge.plantuml.command.CommandPackageEmpty;
import net.sourceforge.plantuml.command.CommandPage;
import net.sourceforge.plantuml.command.CommandRankDir;
import net.sourceforge.plantuml.command.UmlDiagramFactory;
import net.sourceforge.plantuml.command.note.CommandFactoryNote;
import net.sourceforge.plantuml.command.note.CommandFactoryNoteOnEntity;
import net.sourceforge.plantuml.command.note.CommandFactoryNoteOnLink;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexOr;
import net.sourceforge.plantuml.descdiagram.command.CommandNewpage;
import net.sourceforge.plantuml.sysml2.command.CommandSysML2;
import net.sourceforge.plantuml.sysml2.command.CommandSysML2Compartment;
import net.sourceforge.plantuml.sysml2.command.CommandSysML2End;
import net.sourceforge.plantuml.sysml2.command.CommandSysML2Link;
import net.sourceforge.plantuml.sysml2.command.CommandSysML2Start;

public class SysML2DiagramFactory extends UmlDiagramFactory {

	private final ISkinSimple skinParam;

	public SysML2DiagramFactory(ISkinSimple skinParam) {
		this.skinParam = skinParam;
	}

	@Override
	public SysML2Diagram createEmptyDiagram() {
		return new SysML2Diagram(skinParam);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List<Command> createCommands() {
		final List<Command> cmds = new ArrayList<Command>();

        cmds.add(new CommandSysML2());
        cmds.add(new CommandSysML2Compartment());
        cmds.add(new CommandSysML2Start());
        cmds.add(new CommandSysML2End());
        cmds.add(new CommandSysML2Link());

		addCommonHides(cmds);
		cmds.add(new CommandHideShow2());

		cmds.add(new CommandPackage());
		cmds.add(new CommandEndPackage());
		cmds.add(new CommandPackageEmpty());

		cmds.add(new CommandFootboxIgnored());
		cmds.add(new CommandRankDir());

		cmds.add(new CommandNewpage(this));
		cmds.add(new CommandPage());
		cmds.add(new CommandAddMethod());

		final CommandFactoryNoteOnEntity factoryNoteOnEntityCommand = new CommandFactoryNoteOnEntity("state",
				new RegexOr("ENTITY", new RegexLeaf("[\\p{L}0-9_.]+"), //
						new RegexLeaf("[%g][^%g]+[%g]") //
				));
		cmds.add(factoryNoteOnEntityCommand.createMultiLine(true));
		cmds.add(factoryNoteOnEntityCommand.createMultiLine(false));

		cmds.add(factoryNoteOnEntityCommand.createSingleLine());
		final CommandFactoryNoteOnLink factoryNoteOnLinkCommand = new CommandFactoryNoteOnLink();
		cmds.add(factoryNoteOnLinkCommand.createSingleLine());
		cmds.add(factoryNoteOnLinkCommand.createMultiLine(false));
		cmds.add(new CommandUrl());

		final CommandFactoryNote factoryNoteCommand = new CommandFactoryNote();
		cmds.add(factoryNoteCommand.createSingleLine());
		cmds.add(factoryNoteCommand.createMultiLine(false));

		cmds.add(new CommandNamespaceSeparator());

		addTitleCommands(cmds);
		addCommonCommands2(cmds);

		return cmds;
	}

}
