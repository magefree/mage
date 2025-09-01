package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TownGreeter extends CardImpl {

    public TownGreeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, mill four cards. You may put a land card from among them into your hand. If you put a Town card into your hand this way, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TownGreeterEffect()));
    }

    private TownGreeter(final TownGreeter card) {
        super(card);
    }

    @Override
    public TownGreeter copy() {
        return new TownGreeter(this);
    }
}

class TownGreeterEffect extends OneShotEffect {

    TownGreeterEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. You may put a land card from among them into your hand. " +
                "If you put a Town card into your hand this way, you gain 2 life";
    }

    private TownGreeterEffect(final TownGreeterEffect effect) {
        super(effect);
    }

    @Override
    public TownGreeterEffect copy() {
        return new TownGreeterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD_LAND_A);
        target.withNotTarget(true);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.HAND, source, game);
        if (card.hasSubtype(SubType.TOWN, game)) {
            player.gainLife(2, game, source);
        }
        return true;
    }
}
