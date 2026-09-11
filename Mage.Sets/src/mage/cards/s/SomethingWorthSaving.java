package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class SomethingWorthSaving extends CardImpl {

    public SomethingWorthSaving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Mill four cards. You may put a permanent card from among them into your hand. You gain 1 life.
        this.getSpellAbility().addEffect(
            new MillThenPutInHandEffect(4, StaticFilters.FILTER_CARD_PERMANENT).withTextOptions("them")
        );
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private SomethingWorthSaving(final SomethingWorthSaving card) {
        super(card);
    }

    @Override
    public SomethingWorthSaving copy() {
        return new SomethingWorthSaving(this);
    }
}
