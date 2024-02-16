package mage.client.deckeditor.table;

import mage.cards.MageCard;
import mage.client.util.comparators.CardViewComparator;
import mage.cards.RateCard;
import mage.view.CardView;
import org.apache.log4j.Logger;

/**
 * {@link MageCard} comparator. Used to sort cards in Deck Editor Table View
 * pane.
 *
 * @author nantuko
 */
public class MageCardComparator implements CardViewComparator {

    private static final Logger logger = Logger.getLogger(MageCardComparator.class);

    private final int column;
    private final boolean ascending;

    public MageCardComparator(int column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    @Override
    public int compare(CardView a, CardView b) {
        Comparable aCom = 1;
        Comparable bCom = 1;

        switch (column) {
            // #skip
            case 0:
                break;
            // Name
            case 1:
                aCom = a.getName();
                bCom = b.getName();
                if (aCom.equals(bCom) && a.getExpansionSetCode().equals(b.getExpansionSetCode())) {
                    aCom = a.getCardNumber();
                    bCom = b.getCardNumber();
                }
                break;
            // Cost
            case 2:
                aCom = a.getManaValue();
                bCom = b.getManaValue();
                break;
            // Color
            case 3:
                aCom = a.getColorText();
                bCom = b.getColorText();
                break;
            // Type
            case 4:
                aCom = a.getTypeText();
                bCom = b.getTypeText();
                break;
            // Stats, attack and defense
            case 5:
                aCom = (float) -1;
                bCom = (float) -1;
                if (a.isCreature()) {
                    aCom = new Float(a.getPower() + '.' + (a.getToughness().startsWith("-") ? "0" : a.getToughness()));
                }
                if (b.isCreature()) {
                    bCom = new Float(b.getPower() + '.' + (b.getToughness().startsWith("-") ? "0" : b.getToughness()));
                }
                break;
            // Rarity
            case 6:
                aCom = a.getRarity() == null ? 0 : a.getRarity().getSorting();
                bCom = b.getRarity() == null ? 0 : b.getRarity().getSorting();
                break;
            // Set name
            case 7:
                aCom = a.getExpansionSetCode();
                bCom = b.getExpansionSetCode();
                break;
            case 8:
                aCom = Integer.parseInt(a.getCardNumber().replaceAll("[\\D]", ""));
                bCom = Integer.parseInt(b.getCardNumber().replaceAll("[\\D]", ""));
                break;
            case 9:
                aCom = RateCard.rateCard(a, null);
                bCom = RateCard.rateCard(b, null);
                break;
            case 10:
                aCom = a.getColorIdentityStr();
                bCom = b.getColorIdentityStr();
                break;
            default:
                break;
        }

        if (ascending) {
            return aCom.compareTo(bCom);
        } else {
            return bCom.compareTo(aCom);
        }
    }

    @Override
    public String getCategoryName(CardView sample) {
        return "";
    }
}
