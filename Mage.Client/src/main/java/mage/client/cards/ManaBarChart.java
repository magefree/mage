package mage.client.cards;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;

public class ManaBarChart extends JComponent {

    Map<String, Integer> pips_at_cmcs = new HashMap<String, Integer>();

    ManaBarChart() {
    }

    ManaBarChart(Map<String, Integer> pips_at_cmcs) {
        this.pips_at_cmcs = pips_at_cmcs;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();
        Dimension minimum = getMinimumSize();
        Dimension maximum = getMaximumSize();
        preferred.width = Math.min(Math.max(preferred.width, minimum.width), maximum.width);
        preferred.height = Math.min(Math.max(preferred.height, minimum.height), maximum.height);
        return preferred;
    }

    @Override
    public void paint(Graphics g) {
        drawBar((Graphics2D) g, getBounds());
    }

    void drawBar(Graphics2D g, Rectangle area) {
        Pattern regex = Pattern.compile("^([0-9]+)##(.)}");
        HashMap<Integer, Integer> totals_at_cmcs = new HashMap<Integer, Integer>();
        int max_num_pips = 0;
        int max_cmc = 0;

        for (String key : pips_at_cmcs.keySet()) {
            Matcher regexMatcher = regex.matcher(key);
            int num_pips = pips_at_cmcs.get(key);
            while (regexMatcher.find()) {
                String cmc = regexMatcher.group(1);
                int cmc_num = Integer.parseInt(cmc);
                if (max_cmc < cmc_num) {
                    max_cmc = cmc_num;
                }

                int total_at_cmc = 0;
                if (totals_at_cmcs.get(cmc_num) != null) {
                    total_at_cmc = totals_at_cmcs.get(cmc_num);
                }
                totals_at_cmcs.put(cmc_num, total_at_cmc + num_pips);
                if (max_num_pips < total_at_cmc + num_pips) {
                    max_num_pips = total_at_cmc + num_pips;
                }
            }
        }

        if (max_num_pips <= 0) {
            max_num_pips = 1;
        }
        int height_factor = 200 / max_num_pips;
        int width_factor = 200 / (max_cmc + 2);
        if (width_factor > 20) {
            width_factor = 20;
        }
        if (width_factor < 11) {
            width_factor = 11;
        }

        g.setColor(new Color(130, 130, 130));
        for (int i = 0; i < max_num_pips; i++) {
            if (max_num_pips > 10) {
                if (i % 10 == 0) {
                    g.drawLine(0, 200 - 1 - i * height_factor, 400, 200 - 1 - i * height_factor);
                } else if (i % 10 == 5) {
                    Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
                    Stroke oldstroke = g.getStroke();
                    g.setStroke(dashed);
                    g.drawLine(0, 200 - 1 - i * height_factor, 400, 200 - 1 - i * height_factor);
                    g.setStroke(oldstroke);
                }
            } else if (i % 2 == 0) {
                g.drawLine(0, 200 - 1 - i * height_factor, 400, 200 - 1 - i * height_factor);
            } else if (i % 2 == 1) {
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
                Stroke oldstroke = g.getStroke();
                g.setStroke(dashed);
                g.drawLine(0, 200 - 1 - i * height_factor, 400, 200 - 1 - i * height_factor);
                g.setStroke(oldstroke);
            }
        }

        for (int i = 0; i < 17; i++) {
            if (i % 5 == 0) {
                g.drawLine(width_factor * i, 200, width_factor * i, 190);
            } else {
                g.drawLine(width_factor * i, 200, width_factor * i, 195);
            }

        }

        HashMap<Integer, Integer> running_totals_at_cmcs = new HashMap<Integer, Integer>();
        for (String key : pips_at_cmcs.keySet()) {
            Matcher regexMatcher = regex.matcher(key);
            int num_pips = pips_at_cmcs.get(key);
            while (regexMatcher.find()) {
                String cmc = regexMatcher.group(1);
                int cmc_num = Integer.parseInt(cmc);
                String color = regexMatcher.group(2);

                int total_at_cmc = 0;
                if (running_totals_at_cmcs.get(cmc_num) != null) {
                    total_at_cmc = running_totals_at_cmcs.get(cmc_num);
                }

                if (color.equalsIgnoreCase("w")) {
                    g.setColor(Color.WHITE);
                }
                if (color.equalsIgnoreCase("u")) {
                    g.setColor(Color.BLUE);
                }
                if (color.equalsIgnoreCase("b")) {
                    g.setColor(Color.BLACK);
                }
                if (color.equalsIgnoreCase("r")) {
                    g.setColor(Color.RED);
                }
                if (color.equalsIgnoreCase("g")) {
                    g.setColor(Color.GREEN);
                }
                if (color.equalsIgnoreCase("c")) {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fill(new Rectangle2D.Double(cmc_num * width_factor, 200 - 1 - total_at_cmc - num_pips * height_factor, 10, num_pips * height_factor));
                running_totals_at_cmcs.put(cmc_num, total_at_cmc + num_pips * height_factor);
            }
        }

    }
}
