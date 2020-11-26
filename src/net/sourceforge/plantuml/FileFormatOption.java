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

import java.awt.geom.AffineTransform;
import java.io.Serializable;

import net.sourceforge.plantuml.graphic.StringBounder;

/**
 * A FileFormat with some parameters.
 * 
 * 
 * @author Arnaud Roques
 * 
 */
public final class FileFormatOption implements Serializable {

	private final FileFormat fileFormat;
	private final AffineTransform affineTransform;
	private boolean withMetadata;
	private final boolean useRedForError;
	private final String svgLinkTarget;
	private final String hoverColor;
	private final TikzFontDistortion tikzFontDistortion;
	private final double scale;
	private final String preserveAspectRatio;
	private final String watermark;
	
	public interface IGraphicsFactory{};

	private IGraphicsFactory graphicsFactory = null;

	public IGraphicsFactory getGraphicsFactory() {
		return graphicsFactory;
	}
	

	public double getScaleCoef() {
		return scale;
	}
	
	public FileFormatOption(FileFormat fileFormat, IGraphicsFactory graphicsDriver) {
		this(graphicsDriver, fileFormat, null, true, false, "_top", false, null, TikzFontDistortion.getDefault(), 1.0, "none", null);
	}

	public FileFormatOption(FileFormat fileFormat) {
		this(null, fileFormat, null, true, false, "_top", false, null, TikzFontDistortion.getDefault(), 1.0, "none", null);
	}

	public FileFormatOption(FileFormat fileFormat, boolean withMetadata) {
		this(null, fileFormat, null, withMetadata, false, "_top", false, null, TikzFontDistortion.getDefault(), 1.0, "none",
				null);
	}

	private FileFormatOption(IGraphicsFactory graphicsDriver, FileFormat fileFormat, AffineTransform at, boolean withMetadata, boolean useRedForError,
			String svgLinkTarget, boolean debugsvek, String hoverColor, TikzFontDistortion tikzFontDistortion,
			double scale, String preserveAspectRatio, String watermark) {
		this.graphicsFactory = graphicsDriver;
		this.hoverColor = hoverColor;
		this.watermark = watermark;
		this.fileFormat = fileFormat;
		this.affineTransform = at;
		this.withMetadata = withMetadata;
		this.useRedForError = useRedForError;
		this.svgLinkTarget = svgLinkTarget;
		this.debugsvek = debugsvek;
		this.tikzFontDistortion = tikzFontDistortion;
		this.scale = scale;
		this.preserveAspectRatio = preserveAspectRatio;
		if (tikzFontDistortion == null) {
			throw new IllegalArgumentException();
		}
	}

	public StringBounder getDefaultStringBounder() {
		return fileFormat.getDefaultStringBounder(tikzFontDistortion);
	}

	public String getSvgLinkTarget() {
		return svgLinkTarget;
	}

	public final boolean isWithMetadata() {
		return withMetadata;
	}

	public final String getPreserveAspectRatio() {
		return preserveAspectRatio;
	}

	public FileFormatOption withUseRedForError() {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, true, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withTikzFontDistortion(TikzFontDistortion tikzFontDistortion) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, true, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withSvgLinkTarget(String svgLinkTarget) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, useRedForError, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withPreserveAspectRatio(String preserveAspectRatio) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, useRedForError, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withHoverColor(String hoverColor) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, useRedForError, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withScale(double scale) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, useRedForError, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	public FileFormatOption withWartermark(String watermark) {
		return new FileFormatOption(graphicsFactory, fileFormat, affineTransform, withMetadata, useRedForError, svgLinkTarget, debugsvek,
				hoverColor, tikzFontDistortion, scale, preserveAspectRatio, watermark);
	}

	@Override
	public String toString() {
		return fileFormat.toString() + " " + affineTransform;
	}

	public final FileFormat getFileFormat() {
		return fileFormat;
	}

	public AffineTransform getAffineTransform() {
		return affineTransform;
	}

	public final boolean isUseRedForError() {
		return useRedForError;
	}

	private boolean debugsvek = false;

	public void setDebugSvek(boolean debugsvek) {
		this.debugsvek = debugsvek;
	}

	public boolean isDebugSvek() {
		return debugsvek;
	}

	public final String getHoverColor() {
		return hoverColor;
	}

	public void hideMetadata() {
		this.withMetadata = false;
	}

	public final TikzFontDistortion getTikzFontDistortion() {
		return tikzFontDistortion;
	}

	public final String getWatermark() {
		return watermark;
	}

}
