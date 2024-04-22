package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
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
                Zone.BATTLEFIELD, new MoveCountersFromSourceToTargetEffect(), new TapSourceCost()
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
