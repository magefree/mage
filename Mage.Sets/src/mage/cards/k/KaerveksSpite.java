package mage.cards.k;

import java.util.UUID;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;

public final class KaerveksSpite extends CardImpl {

    public KaerveksSpite(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.INSTANT}, "{B}{B}{B}");

        // As an additional cost to cast Kaervek's Spite, sacrifice all permanents you control and discard your hand.
        this.getSpellAbility().addCost(new SacrificeAllCost(new FilterControlledPermanent("permanents you control")));
        this.getSpellAbility().addCost(new DiscardHandCost());

        // Target player loses 5 life.
        Effect effect = new LoseLifeTargetEffect(5);
        this.getSpellAbility().addEffect(effect);
    }

    public KaerveksSpite(final KaerveksSpite other) {
        super(other);
    }

    @Override
    public KaerveksSpite copy() {
        return new KaerveksSpite(this);
    }
}
