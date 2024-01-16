package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Loki
 */
public final class ApocalypseHydra extends CardImpl {

    public ApocalypseHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Apocalypse Hydra enters the battlefield with X +1/+1 counters on it. If X is 5 or more, it enters the battlefield with an additional X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new ApocalypseHydraEffect()));

        // {1}{R}, Remove a +1/+1 counter from Apocalypse Hydra: Apocalypse Hydra deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1, "it"), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ApocalypseHydra(final ApocalypseHydra card) {
        super(card);
    }

    @Override
    public ApocalypseHydra copy() {
        return new ApocalypseHydra(this);
    }
}

class ApocalypseHydraEffect extends OneShotEffect {

    ApocalypseHydraEffect() {
        super(Outcome.BoostCreature);
        staticText = "with X +1/+1 counters on it. If X is 5 or more, it enters the battlefield with an additional X +1/+1 counters on it";
    }

    private ApocalypseHydraEffect(final ApocalypseHydraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            int amount = CardUtil.getSourceCostsTag(game, source, "X", 0);
            if (amount > 0) {
                List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                if (amount < 5) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
                } else {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount * 2), source.getControllerId(), source, game, appliedEffects);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ApocalypseHydraEffect copy() {
        return new ApocalypseHydraEffect(this);
    }
}
