package mage.client.components;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

/**
 * Hyperlinks highlight in chats, game logs and feedback panel texts.
 * <p>
 * Swing text documents and their views are not thread safe: a document change outside the swing thread breaks the
 * text views on the next layout (client errors "StateInvariantError: GlyphView: Stale view", #11612 and many
 * duplicates), so the highlight must be done in the swing thread at once.
 *
 * @author JayDi85
 */
public class MageEditorPaneTest {

    private static final String LINK_TEXT = "Grizzly Bears";

    private static MageEditorPane createPane(List<String> changeThreads) {
        MageEditorPane pane = new MageEditorPane();
        pane.setEditable(false);
        pane.enableHyperlinksAndCardPopups();
        pane.setText("Select a target for <a href=\"#" + LINK_TEXT + "\">" + LINK_TEXT + "</a>");
        pane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeThreads.add(Thread.currentThread().getName());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeThreads.add(Thread.currentThread().getName());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeThreads.add(Thread.currentThread().getName());
            }
        });
        return pane;
    }

    private static Element findLinkElement(MageEditorPane pane) {
        HTMLDocument doc = (HTMLDocument) pane.getDocument();
        try {
            int pos = doc.getText(0, doc.getLength()).indexOf(LINK_TEXT);
            Assert.assertTrue("can't find link in the text", pos >= 0);
            return doc.getCharacterElement(pos + 1);
        } catch (BadLocationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String getLinkDecoration(MageEditorPane pane) {
        Object decoration = findLinkElement(pane).getAttributes().getAttribute(CSS.Attribute.TEXT_DECORATION);
        return decoration == null ? "" : decoration.toString();
    }

    private static void fireLinkEvent(MageEditorPane pane, HyperlinkEvent.EventType type) {
        pane.fireHyperlinkUpdate(new HyperlinkEvent(pane, type, null, "#" + LINK_TEXT, findLinkElement(pane)));
    }

    @Test
    public void test_Highlight_InSwingThreadAtOnce() throws Exception {
        List<String> changeThreads = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        SwingUtilities.invokeAndWait(() -> {
            String swingThread = Thread.currentThread().getName();
            MageEditorPane pane = createPane(changeThreads);

            // mouse over the link
            fireLinkEvent(pane, HyperlinkEvent.EventType.ENTERED);
            if (!getLinkDecoration(pane).equals("underline")) {
                errors.add("link must be underlined at once on mouse enter, found: " + getLinkDecoration(pane));
            }

            // mouse out of the link
            fireLinkEvent(pane, HyperlinkEvent.EventType.EXITED);
            if (!getLinkDecoration(pane).equals("none")) {
                errors.add("link must lose underline at once on mouse exit, found: " + getLinkDecoration(pane));
            }

            changeThreads.stream()
                    .filter(name -> !name.equals(swingThread))
                    .forEach(name -> errors.add("document must be changed in the swing thread only, found: " + name));
        });
        Assert.assertTrue(String.join("\n", errors), errors.isEmpty());
        Assert.assertFalse("must have document changes for underline and its reset", changeThreads.isEmpty());
    }

    @Test
    public void test_Highlight_OwnLinksOnly() throws Exception {
        List<String> changeThreadsA = new ArrayList<>();
        List<String> changeThreadsB = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        SwingUtilities.invokeAndWait(() -> {
            MageEditorPane paneA = createPane(changeThreadsA); // game log
            MageEditorPane paneB = createPane(changeThreadsB); // feedback panel

            // mouse over the link in pane A, then mouse out of the link in pane B
            fireLinkEvent(paneA, HyperlinkEvent.EventType.ENTERED);
            fireLinkEvent(paneB, HyperlinkEvent.EventType.EXITED);

            if (!getLinkDecoration(paneA).equals("underline")) {
                errors.add("pane A: link must keep underline, found: " + getLinkDecoration(paneA));
            }
            if (!changeThreadsB.isEmpty()) {
                errors.add("pane B: document must not be changed by a link of another pane, changes: " + changeThreadsB.size());
            }
        });
        Assert.assertTrue(String.join("\n", errors), errors.isEmpty());
    }
}
