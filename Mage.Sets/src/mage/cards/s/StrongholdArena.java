package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class StrongholdArena extends CardImpl {

    public StrongholdArena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Kicker {G} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{G}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);

        // When Stronghold Arena enters the battlefield, you gain 3 life for each time it was kicked.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StrongholdArenaGainLifeEffect()));

        // Whenever one or more creatures you control deal combat damage to a player, you may reveal the top card of your library and put it into your hand.
        // If you do, you lose life equal to its mana value.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                Zone.BATTLEFIELD, new StrongholdArenaDrawEffect(), false, false, true
        ));
    }

    private StrongholdArena(final StrongholdArena card) {
        super(card);
    }

    @Override
    public StrongholdArena copy() {
        return new StrongholdArena(this);
    }
}

class StrongholdArenaGainLifeEffect extends OneShotEffect {

    public StrongholdArenaGainLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain 3 life for each time it was kicked";
    }

    private StrongholdArenaGainLifeEffect(final StrongholdArenaGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdArenaGainLifeEffect copy() {
        return new StrongholdArenaGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.gainLife(KickerAbility.getSourceObjectKickedCount(game, source) * 3, game, source);
        return true;
    }
}

class StrongholdArenaDrawEffect extends OneShotEffect {

    public StrongholdArenaDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put it into your hand. If you do, you lose life equal to its mana value.";
    }

    private StrongholdArenaDrawEffect(final StrongholdArenaDrawEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdArenaDrawEffect copy() {
        return new StrongholdArenaDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        int manaValue = card.getManaValue();
        controller.revealCards(source, new CardsImpl(card), game);
        if (controller.moveCards(card, Zone.HAND, source, game)) {
            controller.loseLife(manaValue, game, source, false);
        }
        return true;
    }
}
