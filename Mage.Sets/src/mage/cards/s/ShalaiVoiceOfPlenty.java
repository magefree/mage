
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class ShalaiVoiceOfPlenty extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("planeswalkers you control");

    static {
        filter1.add(CardType.PLANESWALKER.getPredicate());
    }

    public ShalaiVoiceOfPlenty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You, planeswalkers you control, and other creatures you control have hexproof.
        Effect effect = new GainAbilityControllerEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("You");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter1);
        effect.setText(", planeswalkers you control");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true);
        effect.setText(", and other creatures you control have hexproof");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {4}{G}{G}: Put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED
                ),
                new ManaCostsImpl<>("{4}{G}{G}")
        ));

    }

    private ShalaiVoiceOfPlenty(final ShalaiVoiceOfPlenty card) {
        super(card);
    }

    @Override
    public ShalaiVoiceOfPlenty copy() {
        return new ShalaiVoiceOfPlenty(this);
    }
}
