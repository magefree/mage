package mage.cards.e;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class ExplodingBarrel extends CardImpl {

    public ExplodingBarrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color. Put a pressure counter on this artifact.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.PRESSURE.createInstance()));
        this.addAbility(ability);

        // {8}, {T}, Sacrifice this artifact: It deals 20 damage to target creature. This ability costs {1} less to activate for each pressure counter on this artifact. Activate only as sorcery.
        Ability ability2 = new ActivateAsSorceryActivatedAbility(
                new DamageTargetEffect(20), new ManaCostsImpl<>("{8}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2.setCostAdjuster(ExplodingBarrelAdjuster.instance));
    }

    private ExplodingBarrel(final ExplodingBarrel card) {
        super(card);
    }

    @Override
    public ExplodingBarrel copy() {
        return new ExplodingBarrel(this);
    }
}

enum ExplodingBarrelAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        int amount = Optional
            .ofNullable(ability.getSourcePermanentIfItStillExists(game))
            .map(permanent -> permanent.getCounters(game).getCount(CounterType.PRESSURE))
            .orElse(0);
        if (amount > 0) {
            CardUtil.reduceCost(ability, amount);
        }
    }
}
