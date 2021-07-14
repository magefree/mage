package mage.cards.f;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaithlessSalvaging extends CardImpl {

    public FaithlessSalvaging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Discard a card, then draw a card.
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private FaithlessSalvaging(final FaithlessSalvaging card) {
        super(card);
    }

    @Override
    public FaithlessSalvaging copy() {
        return new FaithlessSalvaging(this);
    }
}
