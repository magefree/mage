
package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Flickerform extends CardImpl {

    public Flickerform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {2}{W}{W}: Exile enchanted creature and all Auras attached to it. At the beginning of the next end step, return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlickerformEffect(), new ManaCostsImpl<>("{2}{W}{W}")));
    }

    private Flickerform(final Flickerform card) {
        super(card);
    }

    @Override
    public Flickerform copy() {
        return new Flickerform(this);
    }
}

class FlickerformEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public FlickerformEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile enchanted creature and all Auras attached to it. At the beginning of the next end step, return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature";
    }

    public FlickerformEffect(final FlickerformEffect effect) {
        super(effect);
    }

    @Override
    public FlickerformEffect copy() {
        return new FlickerformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Exile enchanted creature and all Auras attached to it.
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
            if (enchantedCreature != null) {
                UUID exileZoneId = UUID.randomUUID();
                enchantedCreature.moveToExile(exileZoneId, enchantment.getName(), source, game);
                for (UUID attachementId : enchantedCreature.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachementId);
                    if (attachment != null && filter.match(attachment, game)) {
                        attachment.moveToExile(exileZoneId, enchantment.getName(), source, game);
                    }
                }
                if (!(enchantedCreature instanceof Token)) {
                    // At the beginning of the next end step, return that card to the battlefield under its owner's control.
                    // If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature
                    AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                            new FlickerformReturnEffect(enchantedCreature.getId(), exileZoneId));
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }

        return false;
    }
}

class FlickerformReturnEffect extends OneShotEffect {

    private static final FilterCard filterAura = new FilterCard();

    static {
        filterAura.add(CardType.ENCHANTMENT.getPredicate());
        filterAura.add(SubType.AURA.getPredicate());
    }

    private final UUID enchantedCardId;
    private final UUID exileZoneId;

    public FlickerformReturnEffect(UUID enchantedCardId, UUID exileZoneId) {
        super(Outcome.Benefit);
        this.enchantedCardId = enchantedCardId;
        this.exileZoneId = exileZoneId;
        this.staticText = "return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature";
    }

    public FlickerformReturnEffect(final FlickerformReturnEffect effect) {
        super(effect);
        this.enchantedCardId = effect.enchantedCardId;
        this.exileZoneId = effect.exileZoneId;
    }

    @Override
    public FlickerformReturnEffect copy() {
        return new FlickerformReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        Card enchantedCard = exileZone.get(enchantedCardId, game);
        //skip if exiled card is missing
        if (enchantedCard != null) {
            Player owner = game.getPlayer(enchantedCard.getOwnerId());
            //skip if card's owner is missing
            if (owner != null) {
                owner.moveCards(enchantedCard, Zone.BATTLEFIELD, source, game);
                Permanent newPermanent = game.getPermanent(enchantedCardId);
                if (newPermanent != null) {
                    Set<Card> toBattlefieldAttached = new HashSet<Card>();
                    for (Card enchantment : exileZone.getCards(game)) {
                        if (filterAura.match(enchantment, game)) {
                            boolean canTarget = false;
                            for (Target target : enchantment.getSpellAbility().getTargets()) {
                                Filter filter = target.getFilter();
                                if (filter.match(newPermanent, game)) {
                                    canTarget = true;
                                    break;
                                }
                            }
                            if (!canTarget) {
                                // Aura stays exiled
                                continue;
                            }
                            game.getState().setValue("attachTo:" + enchantment.getId(), newPermanent);
                        }
                        toBattlefieldAttached.add(enchantment);
                    }
                    if (!toBattlefieldAttached.isEmpty()) {
                        controller.moveCards(toBattlefieldAttached, Zone.BATTLEFIELD, source, game);
                        for (Card card : toBattlefieldAttached) {
                            if (game.getState().getZone(card.getId()) == Zone.BATTLEFIELD) {
                                newPermanent.addAttachment(card.getId(), source, game);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
