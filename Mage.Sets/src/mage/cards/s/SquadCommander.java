package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KorWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquadCommander extends CardImpl {

    public SquadCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Squad Commander enters the battlefield, create a 1/1 white Kor Warrior creature token for each creature in your party.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new KorWarriorToken(), PartyCount.instance)
        ).addHint(PartyCountHint.instance));

        // At the beginning of combat on your turn, if you have a full party, creatures you control get +1/+0 and gain indestructible until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new BoostControlledEffect(
                                1, 0, Duration.EndOfTurn
                        ), TargetController.YOU, false
                ), FullPartyCondition.instance, "At the beginning of combat on your turn, " +
                "if you have a full party, creatures you control get +1/+0 and gain indestructible until end of turn."
        );
        ability.addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.addAbility(ability);
    }

    private SquadCommander(final SquadCommander card) {
        super(card);
    }

    @Override
    public SquadCommander copy() {
        return new SquadCommander(this);
    }
}
