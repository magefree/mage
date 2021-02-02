package mage.cards.b;

import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BarterInBlood extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public BarterInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Each player sacrifices two creatures.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(2, filter));
    }

    private BarterInBlood(final BarterInBlood card) {
        super(card);
    }

    @Override
    public BarterInBlood copy() {
        return new BarterInBlood(this);
    }
}
