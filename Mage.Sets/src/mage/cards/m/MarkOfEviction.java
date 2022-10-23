
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MarkOfEviction extends CardImpl {

    public MarkOfEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, return enchanted creature and all Auras attached to that creature to their owners' hands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MarkOfEvictionEffect(), TargetController.YOU, false));
    }

    private MarkOfEviction(final MarkOfEviction card) {
        super(card);
    }

    @Override
    public MarkOfEviction copy() {
        return new MarkOfEviction(this);
    }
}

class MarkOfEvictionEffect extends OneShotEffect {

    public MarkOfEvictionEffect() {
        super(Outcome.Benefit);
        this.staticText = "return enchanted creature and all Auras attached to that creature to their owners' hands";
    }

    public MarkOfEvictionEffect(final MarkOfEvictionEffect effect) {
        super(effect);
    }

    @Override
    public MarkOfEvictionEffect copy() {
        return new MarkOfEvictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null && sourceObject.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(sourceObject.getAttachedTo());
            if (enchanted != null) {
                Set<Card> toHand = new HashSet<>();
                toHand.add(enchanted);
                for (UUID attachmentId : enchanted.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                        toHand.add(attachment);
                    }
                }
                controller.moveCards(toHand, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
