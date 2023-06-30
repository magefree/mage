
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class UrgorosTheEmptyOne extends CardImpl {

    public UrgorosTheEmptyOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Urgoros, the Empty One deals combat damage to a player, that player discards a card at random. If the player can't, you draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new UrgorosTheEmptyOneEffect(), false, true));
    }

    private UrgorosTheEmptyOne(final UrgorosTheEmptyOne card) {
        super(card);
    }

    @Override
    public UrgorosTheEmptyOne copy() {
        return new UrgorosTheEmptyOne(this);
    }
}

class UrgorosTheEmptyOneEffect extends OneShotEffect {

    public UrgorosTheEmptyOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player discards a card at random. If the player can't, you draw a card";
    }

    public UrgorosTheEmptyOneEffect(final UrgorosTheEmptyOneEffect effect) {
        super(effect);
    }

    @Override
    public UrgorosTheEmptyOneEffect copy() {
        return new UrgorosTheEmptyOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attackedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && attackedPlayer != null) {
            if (attackedPlayer.getHand().isEmpty()) {
                controller.drawCards(1, source, game);
            } else {
                attackedPlayer.discardOne(true, false, source, game);
            }
            return true;
        }
        return false;
    }
}
