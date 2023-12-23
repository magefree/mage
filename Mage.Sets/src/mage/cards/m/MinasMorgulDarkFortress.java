package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class MinasMorgulDarkFortress extends CardImpl {

    public MinasMorgulDarkFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Minas Morgul, Dark Fortress enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {3}{B}, {T}: Put a shadow counter on target creature. For as long as that creature has a shadow counter on it, it's a Wraith in addition to its other types.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.SHADOW.createInstance()),new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new MinasMorgulEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MinasMorgulDarkFortress(final MinasMorgulDarkFortress card) {
        super(card);
    }

    @Override
    public MinasMorgulDarkFortress copy() {
        return new MinasMorgulDarkFortress(this);
    }
}
//Based on Aquitects Will
class MinasMorgulEffect extends AddCardSubTypeTargetEffect {

    MinasMorgulEffect() {
        super(SubType.WRAITH, Duration.Custom);
        staticText = "For as long as that creature has a shadow counter on it, it's a Wraith in addition to its other types";
    }

    private MinasMorgulEffect(final MinasMorgulEffect effect) {
        super(effect);
    }

    @Override
    public MinasMorgulEffect copy() {
        return new MinasMorgulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (creature == null || creature.getCounters(game).getCount(CounterType.SHADOW) < 1) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
