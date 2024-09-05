package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttackInTheBox extends CardImpl {

    public AttackInTheBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Attack-in-the-Box attacks, you may have it get +4/+0 until end of turn. If you do, sacrifice it at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new BoostSourceEffect(
                4, 0, Duration.EndOfTurn
        ).setText("have it get +4/+0 until end of turn"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("If you do, sacrifice it at the beginning of the next end step"));
        this.addAbility(ability);
    }

    private AttackInTheBox(final AttackInTheBox card) {
        super(card);
    }

    @Override
    public AttackInTheBox copy() {
        return new AttackInTheBox(this);
    }
}
