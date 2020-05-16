package mage.cards.k;

import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

public final class KaerveksSpite extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("permanents you control");

    public KaerveksSpite(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.INSTANT}, "{B}{B}{B}");

        // As an additional cost to cast Kaervek's Spite, sacrifice all permanents you control and discard your hand.
        this.getSpellAbility().addCost(new SacrificeAllCost(filter));
        this.getSpellAbility().addCost(new DiscardHandCost());

        // Target player loses 5 life.=
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private KaerveksSpite(final KaerveksSpite other) {
        super(other);
    }

    @Override
    public KaerveksSpite copy() {
        return new KaerveksSpite(this);
    }
}
