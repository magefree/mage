package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.token.Dinosaur31Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class BonehoardDracosaur extends CardImpl {

    public BonehoardDracosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, exile the top two cards of your library. You may play them this turn. If you exiled a land card this way, create a 3/1 red Dinosaur creature token. If you exiled a nonland card this way, create a Treasure token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BoneahoardDracosaurEffect(), TargetController.YOU, false));
    }

    private BonehoardDracosaur(final BonehoardDracosaur card) {
        super(card);
    }

    @Override
    public BonehoardDracosaur copy() {
        return new BonehoardDracosaur(this);
    }
}

class BoneahoardDracosaurEffect extends OneShotEffect {

    BoneahoardDracosaurEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. You may play them this turn. "
                + "If you exiled a land card this way, create a 3/1 red Dinosaur creature token. "
                + "If you exiled a nonland card this way, create a Treasure token.";
    }

    private BoneahoardDracosaurEffect(final BoneahoardDracosaurEffect effect) {
        super(effect);
    }

    @Override
    public BoneahoardDracosaurEffect copy() {
        return new BoneahoardDracosaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<Card> cards = controller.getLibrary().getTopCards(game, 2);
        if (cards.isEmpty()) {
            return true;
        }

        UUID exileId = CardUtil.getExileZoneId("BonehoardDragon::" + source.getSourceId() + "::" + game.getTurn(), game);
        String exileName = CardUtil.getSourceIdName(game, source) + " turn:" + game.getTurnNum();

        controller.moveCardsToExile(cards, source, game, true, exileId, exileName);
        ExileZone zone = game.getExile().getExileZone(exileId);
        zone.setCleanupOnEndTurn(true);

        // remove cards that could not be moved to exile
        cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
        if (!cards.isEmpty()) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn)
                    .setTargetPointer(new FixedTargets(cards, game)), source);
        }

        boolean exiledLand = cards.stream().anyMatch(c -> c.isLand(game));
        boolean exiledNonLand = cards.stream().anyMatch(c -> !c.isLand(game));

        if (exiledLand) {
            new CreateTokenEffect(new Dinosaur31Token())
                    .apply(game, source);
        }
        if (exiledNonLand) {
            new CreateTokenEffect(new TreasureToken())
                    .apply(game, source);
        }

        return true;
    }

}