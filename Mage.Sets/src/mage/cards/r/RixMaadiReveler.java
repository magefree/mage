package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SpectacleCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RixMaadiReveler extends CardImpl {

    public RixMaadiReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spectacle {2}{B}{R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{2}{B}{R}")));

        // When Rix Maadi Reveler enters the battlefield, discard a card, then draw a card. If Rix Maadi Reveler's spectacle cost was paid, instead discard your hand, then draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RixMaadiRevelerEffect(), false));
    }

    private RixMaadiReveler(final RixMaadiReveler card) {
        super(card);
    }

    @Override
    public RixMaadiReveler copy() {
        return new RixMaadiReveler(this);
    }
}

class RixMaadiRevelerEffect extends OneShotEffect {

    RixMaadiRevelerEffect() {
        super(Outcome.Benefit);
        staticText = "discard a card, then draw a card. " +
                "If {this}'s spectacle cost was paid, " +
                "instead discard your hand, then draw three cards.";
    }

    private RixMaadiRevelerEffect(final RixMaadiRevelerEffect effect) {
        super(effect);
    }

    @Override
    public RixMaadiRevelerEffect copy() {
        return new RixMaadiRevelerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (SpectacleCondition.instance.apply(game, source)) {
            player.discard(player.getHand().size(), false, false, source, game);
            player.drawCards(3, source, game);
        } else {
            player.discard(1, false, false, source, game);
            player.drawCards(1, source, game);
        }
        return true;
    }
}
