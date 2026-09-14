package mage.cards.s;

import java.util.UUID;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class SilenceTheEcho extends CardImpl {

    public SilenceTheEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice a creature or planeswalker or pay {3}.
        this.getSpellAbility().addCost(
            new OrCost(
                "sacrifice a creature or planeswalker or pay {3}",
                new SacrificeTargetCost(new FilterCreatureOrPlaneswalkerPermanent()),
                new PayLifeCost(3)
            )
        );

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private SilenceTheEcho(final SilenceTheEcho card) {
        super(card);
    }

    @Override
    public SilenceTheEcho copy() {
        return new SilenceTheEcho(this);
    }
}
