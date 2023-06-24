package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DavrielRogueShadowmage extends CardImpl {

    public DavrielRogueShadowmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DAVRIEL);
        this.setStartingLoyalty(3);

        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, Davriel, Rogue Shadowmage deals 2 damage to them.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD, new DamageTargetEffect(2),
                        TargetController.OPPONENT, false, true
                ), DavrielRogueShadowmageCondition.instance, "At the beginning of each opponent's upkeep, " +
                "if that player has one or fewer cards in hand, {this} deals 2 damage to them."
        ));

        // -1: Target player discards a card.
        Ability ability = new LoyaltyAbility(new DiscardTargetEffect(1), -1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DavrielRogueShadowmage(final DavrielRogueShadowmage card) {
        super(card);
    }

    @Override
    public DavrielRogueShadowmage copy() {
        return new DavrielRogueShadowmage(this);
    }
}

enum DavrielRogueShadowmageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.getHand().size() < 2;
    }
}