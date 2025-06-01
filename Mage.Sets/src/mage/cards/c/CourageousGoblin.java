package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class CourageousGoblin extends CardImpl {

    public CourageousGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks while you control a creature with power 4 or greater, this creature gets +1/+0 and gains menace until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"), false
        ).withTriggerCondition(FerociousCondition.instance);
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.EndOfTurn
        ).setText("and gains menace until end of turn"));
        this.addAbility(ability);
    }

    private CourageousGoblin(final CourageousGoblin card) {
        super(card);
    }

    @Override
    public CourageousGoblin copy() {
        return new CourageousGoblin(this);
    }
}
