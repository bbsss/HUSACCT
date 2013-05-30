package husacct.graphics.presentation.figures;

import husacct.common.Resource;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.ImageFigure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;

public class ClassFigure extends BaseFigure {

	private static final long serialVersionUID = -468596930534802557L;
	protected RectangleFigure top, middle, bottom;
	protected TextFigure classNameText;
	private BufferedImage compIcon;
	private ImageFigure compIconFig;

	public int MIN_WIDTH = 60;
	public int MIN_HEIGHT = 50;

	public ClassFigure(String name) {
		super(name);

		top = new RectangleFigure();
		middle = new RectangleFigure();
		bottom = new RectangleFigure();
		classNameText = new TextFigure(getName());
		classNameText.set(AttributeKeys.FONT_BOLD, true);

		children.add(top);
		children.add(middle);
		children.add(classNameText);
		children.add(bottom);

		compIconFig = new ImageFigure();
		compIconFig.set(AttributeKeys.STROKE_WIDTH, 0.0);
		compIconFig.set(AttributeKeys.FILL_COLOR, defaultBackgroundColor);

		try {
			//TODO There needs to be a icon for Projects
			URL componentImageURL = Resource.get(Resource.ICON_CLASS_PUBLIC);
			compIcon = ImageIO.read(componentImageURL);
			compIconFig.setImage(null, compIcon);
			children.add(compIconFig);
		} catch (Exception e) {
			compIconFig = null;
			Logger.getLogger(this.getClass()).warn("failed to load component icon image file");
		}
		
		set(AttributeKeys.FILL_COLOR, defaultBackgroundColor);
		set(AttributeKeys.CANVAS_FILL_COLOR, defaultBackgroundColor);
	}

	public TextFigure getClassNameText() {
		return classNameText;
	}

	@Override
	public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
		if (lead.x - anchor.x < MIN_WIDTH)
			lead.x = anchor.x + MIN_WIDTH;
		if (lead.y - anchor.y < MIN_HEIGHT)
			lead.y = anchor.y + MIN_HEIGHT;

		double width = lead.x - anchor.x;
		double totalHeight = lead.y - anchor.y;
		double middleHeight = Math.floor(totalHeight / 3);
		double bottomHeight = Math.floor(totalHeight / 3);
		double topHeight = totalHeight - middleHeight - bottomHeight;

		top.setBounds(anchor, new Point2D.Double(anchor.x + width, anchor.y
				+ topHeight));
		middle.setBounds(new Point2D.Double(anchor.x, anchor.y + topHeight),
				new Point2D.Double(anchor.x + width, anchor.y + topHeight
						+ middleHeight));
		bottom.setBounds(new Point2D.Double(anchor.x, anchor.y + topHeight
				+ middleHeight), new Point2D.Double(anchor.x + width, anchor.y
				+ topHeight + middleHeight + bottomHeight));

		// textbox centralising
		double plusX = (top.getBounds().width - classNameText.getBounds().width) / 2;
		double plusY = (top.getBounds().height - classNameText.getBounds().height) / 2;

		Point2D.Double textAnchor = (Double) anchor.clone();
		textAnchor.x += plusX;
		textAnchor.y += plusY;
		classNameText.setBounds(textAnchor, null);
		
		if (compIconFig != null) {
			double iconAnchorX = lead.x - 6 - compIcon.getWidth();
			double iconAnchorY = anchor.y + MIN_HEIGHT;
			double iconLeadX = iconAnchorX + compIcon.getWidth();
			double iconLeadY = iconAnchorY + compIcon.getHeight();
			compIconFig.setBounds(new Point2D.Double(iconAnchorX, iconAnchorY), new Point2D.Double(iconLeadX, iconLeadY));
		}
		this.invalidate();
	}

	@Override
	public ClassFigure clone() {
		ClassFigure other = (ClassFigure) super.clone();

		other.top = top.clone();
		other.middle = middle.clone();
		other.classNameText = classNameText.clone();
		other.bottom = bottom.clone();
		other.compIconFig = compIconFig.clone();
		
		other.children = new ArrayList<Figure>();
		other.children.add(other.top);
		other.children.add(other.middle);
		other.children.add(other.classNameText);
		other.children.add(other.bottom);
		if (compIconFig != null) {
			other.children.add(other.compIconFig);
		}
		
		return other;
	}
}
