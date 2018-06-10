
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RishkarPeemaRenegade extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature you control with a counter on it");

    static {
        filter.add(new CounterAnyPredicate());
    }

    public RishkarPeemaRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rishkar, Peema Renegade enters the battlefield, put a +1/+1 counter on each of up to two target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on each of up to two target creatures");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // Each creature you control with a counter on it has "T: Add G."
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        new GreenManaAbility(),
                        Duration.WhileOnBattlefield,
                        filter)));
    }

    public RishkarPeemaRenegade(final RishkarPeemaRenegade card) {
        super(card);
    }

    @Override
    public RishkarPeemaRenegade copy() {
        return new RishkarPeemaRenegade(this);
    }
}
