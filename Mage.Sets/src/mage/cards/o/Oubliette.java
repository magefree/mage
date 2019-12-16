package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.Filter;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class Oubliette extends CardImpl {

    public Oubliette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // When Oubliette enters the battlefield, exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new OublietteEffect(), false);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // When Oubliette leaves the battlefield, return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new OublietteReturnEffect(), false);
        this.addAbility(ability2);
    }

    public Oubliette(final Oubliette card) {
        super(card);
    }

    @Override
    public Oubliette copy() {
        return new Oubliette(this);
    }
}

class OublietteEffect extends OneShotEffect {

    public OublietteEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature";
    }

    public OublietteEffect(final OublietteEffect effect) {
        super(effect);
    }

    @Override
    public OublietteEffect copy() {
        return new OublietteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            game.getState().setValue(CardUtil.getCardZoneString("savedCounters", source.getSourceId(), game), targetCreature.getCounters(game).copy());
            game.getState().setValue(CardUtil.getCardZoneString("targetId", source.getSourceId(), game), targetCreature.getId());
            Set<Card> toExile = new HashSet<>();
            toExile.add(targetCreature);
            for (UUID attachementId : targetCreature.getAttachments()) {
                Permanent attachment = game.getPermanent(attachementId);
                if (attachment != null && attachment.getSubtype(game).contains(SubType.AURA)) {
                    toExile.add(attachment);
                }
            }
            controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceObject.getIdName());
        }

        return true;
    }
}

class OublietteReturnEffect extends OneShotEffect {

    public OublietteReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent";
    }

    public OublietteReturnEffect(final OublietteReturnEffect effect) {
        super(effect);
    }

    @Override
    public OublietteReturnEffect copy() {
        return new OublietteReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source.getSourceId(), true));
        if (exileZone == null) {
            return true;
        }

        Card exiledCreatureCard = exileZone.get((UUID) game.getState().getValue(CardUtil.getCardZoneString("targetId", source.getSourceId(), game, true)), game);
        if (exiledCreatureCard == null) {
            return false;
        }
        controller.moveCards(exiledCreatureCard, Zone.BATTLEFIELD, source, game, true, false, true, null);
        Permanent newPermanent = game.getPermanent(exiledCreatureCard.getId());
        if (newPermanent != null) {
            // Restore the counters
            Counters counters = (Counters) game.getState().getValue(CardUtil.getCardZoneString("savedCounters", source.getSourceId(), game, true));
            if (counters != null) {
                for (Counter counter : counters.values()) {
                    if (counter != null) {
                        newPermanent.getCounters(game).addCounter(counter); // it's restore counters, not add (e.g. without add events)
                    }
                }
            }
            // readd the attachments
            Set<Card> toBattlefield = new HashSet<>();
            for (Card enchantment : exileZone.getCards(game)) {
                if (enchantment.getSubtype(game).contains(SubType.AURA)) {
                    boolean canTarget = false;
                    for (Target target : enchantment.getSpellAbility().getTargets()) {
                        Filter filter2 = target.getFilter();
                        if (filter2.match(newPermanent, game)) {
                            canTarget = true;
                            break;
                        }
                    }
                    if (!canTarget) {
                        // Aura stays exiled
                        continue;
                    }
                    game.getState().setValue("attachTo:" + enchantment.getId(), newPermanent);
                    toBattlefield.add(enchantment);
                }
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, true, false, true, null);
            for (Card enchantmentCard : toBattlefield) {
                Permanent permanent = game.getPermanent(enchantmentCard.getId());
                if (permanent != null) {
                    newPermanent.addAttachment(permanent.getId(), game);
                }
            }

        }
        return true;
    }
}
