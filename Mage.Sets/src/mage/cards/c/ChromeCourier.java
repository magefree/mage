package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChromeCourier extends CardImpl {

    public ChromeCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Chrome Courier enters the battlefield, reveal the top two cards of your library. Put one of them into your hand and the other into your graveyard. If you put an artifact card into your hand this way, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChromeCourierEffect()));
    }

    private ChromeCourier(final ChromeCourier card) {
        super(card);
    }

    @Override
    public ChromeCourier copy() {
        return new ChromeCourier(this);
    }
}

class ChromeCourierEffect extends OneShotEffect {

    ChromeCourierEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top two cards of your library. " +
                "Put one of them into your hand and the other into your graveyard. " +
                "If you put an artifact card into your hand this way, you gain 3 life";
    }

    private ChromeCourierEffect(final ChromeCourierEffect effect) {
        super(effect);
    }

    @Override
    public ChromeCourierEffect copy() {
        return new ChromeCourierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.revealCards(source, cards, game);
        Card card;
        switch (cards.size()) {
            case 0:
                card = null;
                break;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInLibrary();
                target.withChooseHint("to hand");
                player.choose(outcome, cards, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        player.moveCards(card, Zone.HAND, source, game);
        cards.remove(card);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        if (card != null && card.isArtifact(game)) {
            player.gainLife(3, game, source);
        }
        return true;
    }
}
