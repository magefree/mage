package org.mage.plugins.card.images;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mage.cards.Card;

import org.apache.log4j.Logger;
import org.mage.plugins.card.CardUrl;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.MagicCardsImageSource;
import org.mage.plugins.card.dl.sources.WizardCardsImageSource;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

	private int type;
	private JTextField addr, port;
	private JProgressBar bar;
	private JOptionPane dlg;
	private boolean cancel;
	private JButton close;
	private int cardIndex;
	private ArrayList<CardUrl> cards;
	private ArrayList<CardUrl> cardsInGame;
	private JComboBox jComboBox1;
	private JLabel jLabel1;
	private static boolean offlineMode = false;
	private JCheckBox checkBox;
	private final Object sync = new Object();
	
    private static CardImageSource cardImageSource;
    
	private Proxy p;
	
	private ExecutorService executor = Executors.newFixedThreadPool(10);

	public static final Proxy.Type[] types = Proxy.Type.values();

	public static void main(String[] args) {
		startDownload(null, null);
	}

	public static void startDownload(JFrame frame, Set<Card> allCards) {
		ArrayList<CardUrl> cards = getNeededCards(allCards);

		/*
		 * if (cards == null || cards.size() == 0) {
		 * JOptionPane.showMessageDialog(null,
		 * "All card pictures have been downloaded."); return; }
		 */

		DownloadPictures download = new DownloadPictures(cards);
		JDialog dlg = download.getDlg(frame);
		dlg.setVisible(true);
		dlg.dispose();
		download.setCancel(true);
	}

	public JDialog getDlg(JFrame frame) {
		String title = "Downloading";

		final JDialog dialog = this.dlg.createDialog(frame, title);
		close.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		return dialog;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public DownloadPictures(ArrayList<CardUrl> cards) {
		this.cards = cards;

		this.cardsInGame = new ArrayList<CardUrl>();
		for (CardUrl url : cards) {
			if (url.isExistsInTheGame())
				cardsInGame.add(url);
		}

		addr = new JTextField("Proxy Address");
		port = new JTextField("Proxy Port");
		bar = new JProgressBar(this);

		JPanel p0 = new JPanel();
		p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

		// Proxy Choice
		ButtonGroup bg = new ButtonGroup();
		String[] labels = { "No Proxy", "HTTP Proxy", "SOCKS Proxy" };
		for (int i = 0; i < types.length; i++) {
			JRadioButton rb = new JRadioButton(labels[i]);
			rb.addChangeListener(new ProxyHandler(i));
			bg.add(rb);
			p0.add(rb);
			if (i == 0)
				rb.setSelected(true);
		}

		// Proxy config
		p0.add(addr);
		p0.add(port);

		p0.add(Box.createVerticalStrut(5));
		jLabel1 = new JLabel();
		jLabel1.setText("Please select server:");

		jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);

		p0.add(jLabel1);
		p0.add(Box.createVerticalStrut(5));
		ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[] { "magiccards.info", "wizards.com" });
		jComboBox1 = new JComboBox();
        
        cardImageSource = MagicCardsImageSource.getInstance();

		jComboBox1.setModel(jComboBox1Model);
		jComboBox1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                switch (cb.getSelectedIndex()) {
                    case 0:
                        cardImageSource = MagicCardsImageSource.getInstance();
                        break;
                    case 1:
                        cardImageSource = WizardCardsImageSource.getInstance();
                        break;
                }
            }
        });
		p0.add(jComboBox1);
		p0.add(Box.createVerticalStrut(5));

		// Start
		final JButton b = new JButton("Start download");
		b.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				new Thread(DownloadPictures.this).start();
				b.setEnabled(false);
				checkBox.setEnabled(false);
			}
		});
		p0.add(Box.createVerticalStrut(5));

		// Progress
		p0.add(bar);
		bar.setStringPainted(true);
		int count = cards.size();
		float mb = (count * 70.0f) / 1024;
		bar.setString(String.format(cardIndex == cards.size() ? "%d of %d cards finished! Please close!"
				: "%d of %d cards finished! Please wait! [%.1f Mb]", 0, cards.size(), mb));
		Dimension d = bar.getPreferredSize();
		d.width = 300;
		bar.setPreferredSize(d);

		p0.add(Box.createVerticalStrut(5));
		checkBox = new JCheckBox("Download for current game only.");
		p0.add(checkBox);
		p0.add(Box.createVerticalStrut(5));
		checkBox.setEnabled(!offlineMode);

		checkBox.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					int count = DownloadPictures.this.cardsInGame.size();
					float mb = (count * 70.0f) / 1024;
					bar.setString(String.format(count == 0 ? "No images to download!" : "%d of %d cards finished! Please wait! [%.1f Mb]",
							0, DownloadPictures.this.cardsInGame.size(), mb));
				} else {
					int count = DownloadPictures.this.cards.size();
					float mb = (count * 70.0f) / 1024;
					bar.setString(String.format(cardIndex == count ? "%d of %d cards finished! Please close!"
							: "%d of %d cards finished! Please wait! [%.1f Mb]", 0, count, mb));
				}
			}
		});

		// JOptionPane
		Object[] options = { b, close = new JButton("Cancel") };
		dlg = new JOptionPane(p0, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
	}

	private static ArrayList<CardUrl> getNeededCards(Set<Card> allCards) {

		ArrayList<CardUrl> cardsToDownload = new ArrayList<CardUrl>();

		/**
		 * read all card names and urls
		 */
		ArrayList<CardUrl> allCardsUrls = new ArrayList<CardUrl>();

		try {
			offlineMode = true;

			for (Card card : allCards) {
				if (card.getCardNumber() > 0 && !card.getExpansionSetCode().isEmpty()) {
					CardUrl url = new CardUrl(card.getName(), card.getExpansionSetCode(), card.getCardNumber(), false);
					allCardsUrls.add(url);
				} else {
					if (card.getCardNumber() < 1) {
						System.err.println("There was a critical error!");
						log.error("Card has no collector ID and won't be sent to client: " + card);
					} else if (card.getExpansionSetCode().isEmpty()) {
						System.err.println("There was a critical error!");
						log.error("Card has no set name and won't be sent to client:" + card);
					}
				}
			}

			allCardsUrls.addAll(getTokenCardUrls());
		} catch (Exception e) {
			log.error(e);
		}

		File file;

		/**
		 * check to see which cards we already have
		 */
		for (CardUrl card : allCardsUrls) {
			boolean withCollectorId = false;
			if (card.name.equals("Forest") || card.name.equals("Mountain") || card.name.equals("Swamp") || card.name.equals("Island")
					|| card.name.equals("Plains")) {
				withCollectorId = true;
			}
			file = new File(CardImageUtils.getImagePath(card, withCollectorId));
			if (!file.exists()) {
				cardsToDownload.add(card);
			}
		}

		for (CardUrl card : cardsToDownload) {
			if (card.token) {
				log.info("Card to download: " + card.name + " (Token) " + card.url);
			} else {
				try {
					log.info("Card to download: " + card.name + " (" + card.set + ") "
							+ cardImageSource.generateURL(card.collector, card.set));
				} catch (Exception e) {
					log.error(e);
				}
			}
		}

		return cardsToDownload;
	}

	private static ArrayList<CardUrl> getTokenCardUrls() throws RuntimeException {
		ArrayList<CardUrl> list = new ArrayList<CardUrl>();
		HashSet<String> filter = new HashSet<String>();
		InputStream in = DownloadPictures.class.getClassLoader().getResourceAsStream("card-pictures-tok.txt");
		readImageURLsFromFile(in, list, filter);
		return list;
	}

	private static void readImageURLsFromFile(InputStream in, ArrayList<CardUrl> list, Set<String> filter) throws RuntimeException {
		if (in == null) {
			log.error("resources input stream is null");
			return;
		}

		BufferedReader reader = null;
		InputStreamReader input = null;
		try {
			input = new InputStreamReader(in);
			reader = new BufferedReader(input);
			String line;

			line = reader.readLine();
			while (line != null) {
				line = line.trim();
				if (line.startsWith("|")) { // new format
					String[] params = line.split("\\|");
					if (params.length >= 4) {
						if (params[1].toLowerCase().equals("generate") && params[2].startsWith("TOK:")) {
							String set = params[2].substring(4);
							CardUrl cardUrl = new CardUrl(params[3], set, 0, true);
							cardUrl.token = true;
							cardUrl.url = generateTokenUrl(params[3], set);
							list.add(cardUrl);
						} else {
							CardUrl cardUrl = new CardUrl(params[2], params[1].toUpperCase(), 0, false);
							cardUrl.url = params[3];
							if (cardUrl.set.startsWith("TOK:")) {
								cardUrl.token = true;
								cardUrl.set = cardUrl.set.substring(4);
							}
							list.add(cardUrl);
						}
					} else {
						log.error("wrong format for image urls: " + line);
					}
				}
				line = reader.readLine();
			}

		} catch (Exception ex) {
			log.error(ex);
			throw new RuntimeException("DownloadPictures : readFile() error");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	final static Map<String, String> setNameReplacement = new HashMap<String, String>() {
		{
			put("SOM", "scars-of-mirrodin");
			put("M11", "magic-2011");
			put("ROE", "rise-of-the-eldrazi");
			put("PVC", "duel-decks-phyrexia-vs-the-coalition");
			put("WWK", "worldwake");
			put("ZEN", "zendikar");
			put("HOP", "planechase");
			put("M10", "magic-2010");
			put("GVL", "duel-decks-garruk-vs-liliana");
			put("ARB", "alara-reborn");
			put("DVD", "duel-decks-divine-vs-demonic");
			put("CON", "conflux");
			put("JVC", "duel-decks-jace-vs-chandra");
			put("ALA", "shards-of-alara");
			put("EVE", "eventide");
			put("SHM", "shadowmoor");
			put("EVG", "duel-decks-elves-vs-goblins");
			put("MOR", "morningtide");
			put("LRW", "lorwyn");
			put("10E", "tenth-edition");
			put("CSP", "coldsnap");
		}
		private static final long serialVersionUID = 1L;
	};

	private static String generateTokenUrl(String name, String set) {
		String _name = name.replaceAll(" ", "-").toLowerCase();
		String _set = "not-supported-set";
		if (setNameReplacement.containsKey(set)) {
			_set = setNameReplacement.get(set);
		} else {
			_set += "-" + set;
		}
		String url = "http://magiccards.info/extras/token/" + _set + "/" + _name + ".jpg";
		return url;
	}

	private class ProxyHandler implements ChangeListener {
		private int type;

		public ProxyHandler(int type) {
			this.type = type;
		}

        @Override
		public void stateChanged(ChangeEvent e) {
			if (((AbstractButton) e.getSource()).isSelected()) {
				DownloadPictures.this.type = type;
				addr.setEnabled(type != 0);
				port.setEnabled(type != 0);
			}
		}
	}

    @Override
	public void run() {
		BufferedInputStream in;
		BufferedOutputStream out;

		File base = new File(Constants.IO.imageBaseDir);
		if (!base.exists()) {
			base.mkdir();
		}

		if (type == 0)
			p = Proxy.NO_PROXY;
		else
			try {
				p = new Proxy(types[type], new InetSocketAddress(addr.getText(), Integer.parseInt(port.getText())));
			} catch (Exception ex) {
				throw new RuntimeException("Gui_DownloadPictures : error 1 - " + ex);
			}

		if (p != null) {
			HashSet<String> ignoreUrls = SettingsManager.getIntance().getIgnoreUrls();

			update(0);
			for (int i = 0; (checkBox.isSelected() ? i < cardsInGame.size() : i < cards.size()) && !cancel; i++) {
				try {

					CardUrl card = checkBox.isSelected() ? cardsInGame.get(i) : cards.get(i);

					log.info("Downloading card: " + card.name + " (" + card.set + ")");

					URL url = new URL(cardImageSource.generateURL(card.collector, card.set));
					if (ignoreUrls.contains(card.set) || card.token) {
						if (card.collector != 0) {
							continue;
						}
						url = new URL(card.url);
					}
					
					Runnable task = new DownloadTask(card, url);
					executor.execute(task);
				} catch (Exception ex) {
					log.error(ex, ex);
				}
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {}
			}
		}
		close.setText("Close");
	}
	
	private final class DownloadTask implements Runnable {
		private CardUrl card;
		private URL url;
		
		public DownloadTask(CardUrl card, URL url) {
			this.card = card;
			this.url = url;
		}
		
        @Override
		public void run() {
			try {
				BufferedInputStream in = new BufferedInputStream(url.openConnection(p).getInputStream());
	
				createDirForCard(card);
	
				boolean withCollectorId = false;
				if (card.name.equals("Forest") || card.name.equals("Mountain") || card.name.equals("Swamp")
						|| card.name.equals("Island") || card.name.equals("Plains")) {
					withCollectorId = true;
				}
				File fileOut = new File(CardImageUtils.getImagePath(card, withCollectorId));
	
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut));
	
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = in.read(buf)) != -1) {
					// user cancelled
					if (cancel) {
						in.close();
						out.flush();
						out.close();
						// delete what was written so far
						fileOut.delete();
					}
					out.write(buf, 0, len);
				}
	
				in.close();
				out.flush();
				out.close();
				
				synchronized (sync) {
					update(cardIndex + 1);
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		
		}
	}

	private static File createDirForCard(CardUrl card) throws Exception {
		File setDir = new File(CardImageUtils.getImageDir(card));
		if (!setDir.exists()) {
			setDir.mkdirs();
		}
		return setDir;
	}

	private void update(int card) {
		this.cardIndex = card;
		if (checkBox.isSelected()) {
			int count = DownloadPictures.this.cardsInGame.size();
			int countLeft = count - card;
			float mb = (countLeft * 70.0f) / 1024;
			bar.setString(String.format(card == count ? "%d of %d cards finished! Please close!"
							: "%d of %d cards finished! Please wait!  [%.1f Mb]",
							card, count, mb));
		} else {
			int count = DownloadPictures.this.cards.size();
			int countLeft = count - card;
			float mb = (countLeft * 70.0f) / 1024;
			bar.setString(String.format(cardIndex == count ? "%d of %d cards finished! Please close!"
							: "%d of %d cards finished! Please wait! [%.1f Mb]",
							card, count, mb));
		}
	}

	private static final Logger log = Logger.getLogger(DownloadPictures.class);

	private static final long serialVersionUID = 1L;
}