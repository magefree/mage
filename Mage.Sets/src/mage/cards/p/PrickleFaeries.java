package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrickleFaeries extends CardImpl {

    public PrickleFaeries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Prickle Faeries deals 2 damage to them.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD, new DamageTargetEffect(2),
                        TargetController.OPPONENT, false, true
                ), PrickleFaeriesCondition.instance, "At the beginning of each opponent's upkeep, " +
                "if that player has two or fewer cards in hand, {this} deals 2 damage to them."
        ));
    }

    private PrickleFaeries(final PrickleFaeries card) {
        super(card);
    }

    @Override
    public PrickleFaeries copy() {
        return new PrickleFaeries(this);
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