package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class LilianasSpoils extends CardImpl {

    private static final FilterCard filter = new FilterCard("a black card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public LilianasSpoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Look at the top five cards of your library. You may reveal a black card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(5), false, StaticValue.get(1), filter,
                Zone.LIBRARY, false, true, false, Zone.HAND, true, false, false
        ).setBackInRandomOrder(true).setText("<br>Look at the top five cards of your library. "
                + "You may reveal a black card from among them and put it into your hand. "
                + "Put the rest on the bottom of your library in a random order.")
        );
    }

    private LilianasSpoils(final LilianasSpoils card) {
        super(card);
    }

    @Override
    public LilianasSpoils copy() {
        return new LilianasSpoils(this);
    }
}
