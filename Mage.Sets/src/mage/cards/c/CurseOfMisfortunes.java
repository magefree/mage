
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfMisfortunes extends CardImpl {

    public CurseOfMisfortunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CurseOfMisfortunesEffect(), true));
    }

    private CurseOfMisfortunes(final CurseOfMisfortunes card) {
        super(card);
    }

    @Override
    public CurseOfMisfortunes copy() {
        return new CurseOfMisfortunes(this);
    }
}

class CurseOfMisfortunesEffect extends OneShotEffect {

    public CurseOfMisfortunesEffect() {
        super(Outcome.Detriment);
        staticText = "you may search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle";
    }

    public CurseOfMisfortunesEffect(final CurseOfMisfortunesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (controller != null && enchantment != null && enchantment.getAttachedTo() != null) {
            Player targetPlayer = game.getPlayer(enchantment.getAttachedTo());
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && targetPlayer != null) {
                FilterCard filter = new FilterCard("Curse card that doesn't have the same name as a Curse attached to enchanted player");
                filter.add(SubType.CURSE.getPredicate());
                // get the names of attached Curses
                for (UUID attachmentId: targetPlayer.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.CURSE, game)) {
                        filter.add(Predicates.not(new NamePredicate(attachment.getName())));
                    }
                }
                TargetCardInLibrary targetCard = new TargetCardInLibrary(filter);
                if (player.searchLibrary(targetCard, source, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        this.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                        game.getState().setValue("attachTo:" + card.getId(), targetPlayer.getId());
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            targetPlayer.addAttachment(card.getId(), source, game);
                        }
                    }
                }
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public CurseOfMisfortunesEffect copy() {
        return new CurseOfMisfortunesEffect(this);
    }

}
