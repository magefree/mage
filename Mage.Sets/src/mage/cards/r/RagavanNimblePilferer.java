package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagavanNimblePilferer extends CardImpl {

    public RagavanNimblePilferer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ragavan, Nimble Pilferer deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, true
        );
        ability.addEffect(new RagavanNimblePilfererEffect());
        this.addAbility(ability);

        // Dash {1}{R}
        this.addAbility(new DashAbility("{1}{R}"));
    }

    private RagavanNimblePilferer(final RagavanNimblePilferer card) {
        super(card);
    }

    @Override
    public RagavanNimblePilferer copy() {
        return new RagavanNimblePilferer(this);
    }
}

class RagavanNimblePilfererEffect extends OneShotEffect {

    RagavanNimblePilfererEffect() {
        super(Outcome.Benefit);
        staticText = "and exile the top card of that player's library. Until end of turn, you may cast that card";
    }

    private RagavanNimblePilfererEffect(final RagavanNimblePilfererEffect effect) {
        super(effect);
    }

    @Override
    public RagavanNimblePilfererEffect copy() {
        return new RagavanNimblePilfererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU, Duration.EndOfTurn, false, false, true
        );
    }
}
