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
package net.sourceforge.plantuml.png;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.security.ImageIO;
import net.sourceforge.plantuml.security.SFile;

public class PngIO {

	private static final String copyleft = "Generated by http://plantuml.com";

	public static void write(RenderedImage image, SFile file, int dpi) throws IOException {
		write(image, file, null, dpi);
	}

	public static void write(RenderedImage image, OutputStream os, int dpi) throws IOException {
		write(image, os, null, dpi);
	}

	public static void write(RenderedImage image, SFile file, String metadata, int dpi) throws IOException {
		OutputStream os = null;
		try {
			os = file.createBufferedOutputStream();
			write(image, os, metadata, dpi);
		} finally {
			if (os != null) {
				os.close();
			}
		}
		Log.debug("File is " + file);
		Log.debug("File size " + file.length());
		if (file.length() == 0) {
			Log.error("File size is zero: " + file);
			ImageIO.write(image, "png", file);
		}
	}

	public static void write(RenderedImage image, OutputStream os, String metadata, int dpi) throws IOException {
		write(image, os, metadata, dpi, null);
	}

	public static void write(RenderedImage image, OutputStream os, String metadata, int dpi, String debugData)
			throws IOException {
		if (forceImageIO == false && metadata != null && checkPNGMetadata()) {
			PngIOMetadata.writeWithMetadata(image, os, metadata, dpi, debugData);
		} else {
			ImageIO.write(image, "png", os);
		}
	}

//	/** writes a BufferedImage of type TYPE_INT_ARGB to PNG using PNGJ */
//	public static void writeARGB(BufferedImage bi, OutputStream os, String metadata) {
//		// if (bi.getType() != BufferedImage.TYPE_INT_ARGB)
//		// throw new PngjException("This method expects  BufferedImage.TYPE_INT_ARGB");
//		ImageInfo imi = new ImageInfo(bi.getWidth(), bi.getHeight(), 8, false);
//		PngChunkTEXT chunkText = new PngChunkTEXT(imi, "copyleft", copyleft);
//		// PngChunkTEXT chunkTextDebug = new PngChunkTEXT(imi, "debug", "debugData");
//		PngChunkITXT meta = new PngChunkITXT(imi);
//		meta.setKeyVal("plantuml", metadata);
//		meta.setCompressed(true);
//
//		PngWriter pngw = new PngWriter(os, imi);
//		pngw.setCompLevel(9);// maximum compression, not critical usually
//		// pngw.setFilterType(FilterType.FILTER_ADAPTIVE_FAST); // see what you prefer here
//		// pngw.setFilterType(FilterType.FILTER_ADAPTIVE_MEDIUM); // see what you prefer here
//		pngw.setFilterType(FilterType.FILTER_ADAPTIVE_FULL); // see what you prefer here
//		pngw.queueChunk(chunkText);
//		// // pngw.queueChunk(chunkTextDebug);
//		pngw.queueChunk(meta);
//		DataBufferInt db = ((DataBufferInt) bi.getRaster().getDataBuffer());
//		SinglePixelPackedSampleModel samplemodel = (SinglePixelPackedSampleModel) bi.getSampleModel();
//		if (db.getNumBanks() != 1)
//			throw new PngjException("This method expects one bank");
//		ImageLineInt line = new ImageLineInt(imi);
//		for (int row = 0; row < imi.rows; row++) {
//			int elem = samplemodel.getOffset(0, row);
//			for (int col = 0, j = 0; col < imi.cols; col++) {
//				int sample = db.getElem(elem++);
//				line.scanline[j++] = (sample & 0xFF0000) >> 16; // R
//				line.scanline[j++] = (sample & 0xFF00) >> 8; // G
//				line.scanline[j++] = (sample & 0xFF); // B
//				// line.scanline[j++] = (((sample & 0xFF000000) >> 24) & 0xFF); // A
//			}
//			pngw.writeRow(line, row);
//		}
//		pngw.end();
//	}

	public static boolean forceImageIO = false;

	static boolean checkPNGMetadata() {
		try {
			final Class cl = Class.forName("com.sun.imageio.plugins.png.PNGMetadata");
			if (cl == null) {
				Log.info("Cannot load com.sun.imageio.plugins.png.PNGMetadata");
				forceImageIO = true;
				return false;
			}
			Log.info("Ok for com.sun.imageio.plugins.png.PNGMetadata");
			return true;
		} catch (Exception e) {
			Log.info("Error loading com.sun.imageio.plugins.png.PNGMetadata " + e);
			forceImageIO = true;
			return false;
		}
	}

}