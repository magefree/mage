
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class ThranGolem extends CardImpl {

    public ThranGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as Thran Golem is enchanted, it gets +2/+2 and has flying, first strike, and trample.
        EnchantedSourceCondition enchanted = new EnchantedSourceCondition();
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), enchanted,
            "As long as {this} is enchanted, it gets +2/+2"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            enchanted, "and has flying"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
            enchanted, ", first strike"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()),
            enchanted, ", and trample."));
        this.addAbility(ability);
    }

    private ThranGolem(final ThranGolem card) {
        super(card);
    }

    @Override
    public ThranGolem copy() {
        return new ThranGolem(this);
    }
}
