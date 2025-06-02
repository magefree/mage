package mage.cards.t;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphantReckoning extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact, enchantment, and planeswalker cards");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public TriumphantReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}{W}");

        // Return all artifact, enchantment, and planeswalker cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter));
    }

    private TriumphantReckoning(final TriumphantReckoning card) {
        super(card);
    }

    @Override
    public TriumphantReckoning copy() {
        return new TriumphantReckoning(this);
    }
}
