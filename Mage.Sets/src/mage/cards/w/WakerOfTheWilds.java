
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.WallOfResurgenceToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class WakerOfTheWilds extends CardImpl {

    public WakerOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{G}{G}: Put X +1/+1 counters on target land you control. That land becomes a 0/0 Elemental creature with haste that's still a land.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance(0),
                        ManacostVariableValue.REGULAR
                ).setText("Put X +1/+1 counters on target land you control."),
                new ManaCostsImpl<>("{X}{G}{G}")
        );
        Effect effect = new BecomesCreatureTargetEffect(new WallOfResurgenceToken(), false, true, Duration.Custom);
        effect.setText("That land becomes a 0/0 Elemental creature with haste. It's still a land");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private WakerOfTheWilds(final WakerOfTheWilds card) {
        super(card);
    }

    @Override
    public WakerOfTheWilds copy() {
        return new WakerOfTheWilds(this);
    }
}
