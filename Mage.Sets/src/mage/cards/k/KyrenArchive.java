
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class KyrenArchive extends CardImpl {

    public KyrenArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your upkeep, you may exile the top card of your library face down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new KyrenArchiveExileEffect(), TargetController.YOU, true));

        // {5}, Discard your hand, Sacrifice Kyren Archive: Put all cards exiled with Kyren Archive into their owner's hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new KyrenArchiveReturnEffect(),
                new GenericManaCost(5)
        );
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KyrenArchive(final KyrenArchive card) {
        super(card);
    }

    @Override
    public KyrenArchive copy() {
        return new KyrenArchive(this);
    }
}

class KyrenArchiveExileEffect extends OneShotEffect {

    KyrenArchiveExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top card of your library face down";
    }

    private KyrenArchiveExileEffect(final KyrenArchiveExileEffect effect) {
        super(effect);
    }

    @Override
    public KyrenArchiveExileEffect copy() {
        return new KyrenArchiveExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                card.setFaceDown(true, game);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                card.setFaceDown(true, game);
                return true;
            }
        }
        return false;
    }
}

class KyrenArchiveReturnEffect extends OneShotEffect {

    KyrenArchiveReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Put all cards exiled with {this} into their owners' hands";
    }

    private KyrenArchiveReturnEffect(final KyrenArchiveReturnEffect effect) {
        super(effect);
    }

    @Override
    public KyrenArchiveReturnEffect copy() {
        return new KyrenArchiveReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            if (exileZone != null) {
                controller.moveCards(exileZone, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
