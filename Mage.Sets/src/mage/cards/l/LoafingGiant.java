package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public final class LoafingGiant extends CardImpl {

    public LoafingGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever Loafing Giant attacks or blocks, put the top card of your library into your graveyard. If that card is a land card, prevent all combat damage Loafing Giant would deal this turn.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new LoafingGiantEffect(), false));
    }

    public LoafingGiant(final LoafingGiant card) {
        super(card);
    }

    @Override
    public LoafingGiant copy() {
        return new LoafingGiant(this);
    }
}

class LoafingGiantEffect extends OneShotEffect {

    public LoafingGiantEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Put the top card of your library into your graveyard. If that card is a land card, prevent all combat damage {this} would deal this turn.";
    }

    public LoafingGiantEffect(final LoafingGiantEffect effect) {
        super(effect);
    }

    @Override
    public LoafingGiantEffect copy() {
        return new LoafingGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                player.moveCards(card, Zone.GRAVEYARD, source, game);
                if (card.isLand()) {
                    game.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}