package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfEldraine extends TransformingDoubleFacedCard {

    public InvasionOfEldraine(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{B}",
                "Prickle Faeries",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE}, "B"
        );
        this.getLeftHalfCard().setStartingDefense(4);
        this.getRightHalfCard().setPT(2, 2);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Eldraine enters the battlefield, target opponent discards two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Prickle Faeries
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Prickle Faeries deals 2 damage to them.
        this.getRightHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD, new DamageTargetEffect(2),
                        TargetController.OPPONENT, false, true
                ), PrickleFaeriesCondition.instance, "At the beginning of each opponent's upkeep, " +
                "if that player has two or fewer cards in hand, {this} deals 2 damage to them."
        ));
    }

    private InvasionOfEldraine(final InvasionOfEldraine card) {
        super(card);
    }

    @Override
    public InvasionOfEldraine copy() {
        return new InvasionOfEldraine(this);
    }
}

enum PrickleFaeriesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getActivePlayerId())
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0) <= 2;
    }
}
