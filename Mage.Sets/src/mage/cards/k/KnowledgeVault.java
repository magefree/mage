
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000 & L_J
 */
public final class KnowledgeVault extends CardImpl {

    public KnowledgeVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Exile the top card of your library face down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnowledgeVaultExileEffect(), new GenericManaCost(2)));

        // {0}: Sacrifice Knowledge Vault. If you do, discard your hand, then put all cards exiled with Knowledge Vault into their owner’s hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnowledgeVaultReturnEffect(), new GenericManaCost(0)));

        // When Knowledge Vault leaves the battlefield, put all cards exiled with Knowledge Vault into their owner’s graveyard.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.GRAVEYARD), false));
    }

    private KnowledgeVault(final KnowledgeVault card) {
        super(card);
    }

    @Override
    public KnowledgeVault copy() {
        return new KnowledgeVault(this);
    }
}

class KnowledgeVaultExileEffect extends OneShotEffect {

    KnowledgeVaultExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top card of your library face down";
    }

    private KnowledgeVaultExileEffect(final KnowledgeVaultExileEffect effect) {
        super(effect);
    }

    @Override
    public KnowledgeVaultExileEffect copy() {
        return new KnowledgeVaultExileEffect(this);
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

class KnowledgeVaultReturnEffect extends OneShotEffect {

    KnowledgeVaultReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Sacrifice {this}. If you do, discard your hand, then put all cards exiled with {this} into their owners' hands";
    }

    private KnowledgeVaultReturnEffect(final KnowledgeVaultReturnEffect effect) {
        super(effect);
    }

    @Override
    public KnowledgeVaultReturnEffect copy() {
        return new KnowledgeVaultReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && controller != null) {
            if (sourcePermanent.sacrifice(source, game)) {
                new DiscardHandControllerEffect().apply(game, source);
                ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
                if (exileZone != null) {
                    controller.moveCards(exileZone, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
