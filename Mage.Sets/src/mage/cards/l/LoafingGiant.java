package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
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

    private LoafingGiant(final LoafingGiant card) {
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
        this.staticText = "Mill a card. If a land card was milled this way, prevent all combat damage {this} would deal this turn.";
    }

    private LoafingGiantEffect(final LoafingGiantEffect effect) {
        super(effect);
    }

    @Override
    public LoafingGiantEffect copy() {
        return new LoafingGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null
                && player
                .millCards(1, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(card -> card.isLand(game))) {
            game.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}