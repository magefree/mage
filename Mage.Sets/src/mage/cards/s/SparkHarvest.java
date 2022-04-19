package mage.cards.s;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparkHarvest extends CardImpl {

    public SparkHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or pay {3}{B}.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature or pay {3}{B}", new SacrificeTargetCost(new TargetControlledCreaturePermanent()),
                new ManaCostsImpl<>("{3}{B}")
        ));

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private SparkHarvest(final SparkHarvest card) {
        super(card);
    }

    @Override
    public SparkHarvest copy() {
        return new SparkHarvest(this);
    }
}
