package mage.cards.j;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AhmadYProjects
 */
public final class JaceThePerfectedMind extends CardImpl {

    public JaceThePerfectedMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U/P}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(5);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Until your next turn, up to one target creature gets -3/-0.
        // -2: Target player mills three cards. Then if a graveyard has twenty or more cards in it, you draw three cards. Otherwise, you draw a card.
        // -X: Target player mills three times X cards.
    }

    private JaceThePerfectedMind(final JaceThePerfectedMind card) {
        super(card);
    }

    @Override
    public JaceThePerfectedMind copy() {
        return new JaceThePerfectedMind(this);
    }
}
