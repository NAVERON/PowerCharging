package javaOpenCV;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class Perspective {

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			//F:/GitProject/LearnByDoing/JavaFx/Almas/src/assets/pics/lena.png      C:/Users/ERON/Desktop/lena.png
			//使用绝对路径，使用相对路径时，跟是在当前工程目录中，所以需要注意
			Mat src = Imgcodecs.imread("./src/javaOpenCV/lena.png");
			// 读取图像到矩阵中
			if (src.empty()) {
				throw new Exception("no file");
			}
			//  cols() rows()获取矩阵  行和列
			int xMargin, yMargin;
			int x0 = src.cols() / 4;
			int x1 = (src.cols() / 4) * 3;
			int y0 = src.cols() / 4;
			int y1 = (src.cols() / 4) * 3;
			Mat dst = new Mat();

			List<Point> listSrcs = java.util.Arrays.asList(
					new Point(x0, y0),
					new Point(x0, y1),
					new Point(x1, y1),
					new Point(x1, y0)
					);
			Mat srcPoints = Converters.vector_Point_to_Mat(listSrcs, CvType.CV_32F);

			xMargin = src.cols() / 10;
			yMargin = src.rows() / 10;
			List<Point> listDsts = java.util.Arrays.asList(new Point(x0 + xMargin, y0 + yMargin), listSrcs.get(1),
					listSrcs.get(2), new Point(x1 - xMargin, y0 + yMargin));
			Mat dstPoints = Converters.vector_Point_to_Mat(listDsts, CvType.CV_32F);

			Mat perspectiveMmat = Imgproc.getPerspectiveTransform(srcPoints, dstPoints);
			Imgproc.warpPerspective(src, dst, perspectiveMmat, src.size(), Imgproc.INTER_LINEAR);
			Imgcodecs.imwrite("./src/javaOpenCV/dst0.jpg", dst);

			xMargin = src.cols() / 8;
			yMargin = src.cols() / 8;
			listDsts.set(0, listSrcs.get(0));
			listDsts.set(1, listSrcs.get(1));
			listDsts.set(2, new Point(x1 - xMargin, y1 - yMargin));
			listDsts.set(3, new Point(x1 - xMargin, y0 - yMargin));
			dstPoints = Converters.vector_Point_to_Mat(listDsts, CvType.CV_32F);

			perspectiveMmat = Imgproc.getPerspectiveTransform(srcPoints, dstPoints);
			Imgproc.warpPerspective(src, dst, perspectiveMmat, src.size(), Imgproc.INTER_LINEAR);
			Imgcodecs.imwrite("./src/javaOpenCV/dst1.jpg", dst);

			xMargin = src.cols() / 6;
			yMargin = src.cols() / 6;
			listDsts.set(0, new Point(x0 + xMargin, y0 + yMargin));
			listDsts.set(1, listSrcs.get(1));
			listDsts.set(2, new Point(x1 - xMargin, y1 - yMargin));
			listDsts.set(3, listSrcs.get(3));
			dstPoints = Converters.vector_Point_to_Mat(listDsts, CvType.CV_32F);

			perspectiveMmat = Imgproc.getPerspectiveTransform(srcPoints, dstPoints);
			Imgproc.warpPerspective(src, dst, perspectiveMmat, src.size(), Imgproc.INTER_LINEAR);

			Imgcodecs.imwrite("./src/javaOpenCV/dst2.jpg", dst);
		} catch (Exception e) {
			System.out.println("例外：" + e);
		}

	}

}
