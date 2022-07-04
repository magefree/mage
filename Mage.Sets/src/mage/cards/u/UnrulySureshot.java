
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class UnrulySureshot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public UnrulySureshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Unruly Sureshot enters the battlefield, put a bounty counter on target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {3}{R}: Unruly Sureshot deals 2 damage to target creature with a bounty counter on it.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{3}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private UnrulySureshot(final UnrulySureshot card) {
        super(card);
    }

    @Override
    public UnrulySureshot copy() {
        return new UnrulySureshot(this);
    }
}
