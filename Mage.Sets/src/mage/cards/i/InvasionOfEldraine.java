package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfEldraine extends TransformingDoubleFacedCard {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, TargetController.ACTIVE);

    public InvasionOfEldraine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{B}",
                "Prickle Faeries",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE}, "B"
        );

        // Invasion of Eldraine
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Eldraine enters the battlefield, target opponent discards two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Prickle Faeries
        this.getRightHalfCard().setPT(2, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Prickle Faeries deals 2 damage to them.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, TargetController.OPPONENT,
                new DamageTargetEffect(2).withTargetDescription("them"), false
        ).withInterveningIf(condition));
    }

    private InvasionOfEldraine(final InvasionOfEldraine card) {
        super(card);
    }

    @Override
    public InvasionOfEldraine copy() {
        return new InvasionOfEldraine(this);
    }
}
