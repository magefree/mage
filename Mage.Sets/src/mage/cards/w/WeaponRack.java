package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaponRack extends CardImpl {

    public WeaponRack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Weapon Rack enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                "with three +1/+1 counters on it"
        ));

        // {T}: Move a +1/+1 counter from Weapon Rack onto target creature. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new WeaponRackEffect(), new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WeaponRack(final WeaponRack card) {
        super(card);
    }

    @Override
    public WeaponRack copy() {
        return new WeaponRack(this);
    }
}

class WeaponRackEffect extends OneShotEffect {

    WeaponRackEffect() {
        super(Outcome.Benefit);
        staticText = "move a +1/+1 counter from {this} onto target creature";
    }

    private WeaponRackEffect(final WeaponRackEffect effect) {
        super(effect);
    }

    @Override
    public WeaponRackEffect copy() {
        return new WeaponRackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.getCounters(game).containsKey(CounterType.P1P1)) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game)) {
            return false;
        }
        sourcePermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
        return true;
    }
}