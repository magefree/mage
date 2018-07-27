package mage.cards.z;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.command.emblems.ZeratulNezarimPrelateEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class ZeratulNezarimPrelate extends CardImpl {

    public static final String EXILE_KEY = "Zeratul, Nezarim Prelate Exile";

    public ZeratulNezarimPrelate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{U}");
        
        this.subtype.add(SubType.ZERATUL);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Exile the top card of target player's library. If it was a creature card, you gain 1 life.
        Ability ability = new LoyaltyAbility(new ZeratulNezarimPrelateFirstEffect(), 1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -3: Put target nonland permanent into its owner's library second from the top.
        ability = new LoyaltyAbility(new ZeratulNezarimPrelateSecondEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // -7: Exile all other nonland permanents. You gain an emblem with "You may play cards exiled with Zeratul, Nezarim Prelate."
        ability = new LoyaltyAbility(new ZeratulNezarimPrelateThirdEffect(), -5);
        ability.addEffect(new GetEmblemEffect(new ZeratulNezarimPrelateEmblem()));
        this.addAbility(ability);
    }

    public ZeratulNezarimPrelate(final ZeratulNezarimPrelate card) {
        super(card);
    }

    @Override
    public ZeratulNezarimPrelate copy() {
        return new ZeratulNezarimPrelate(this);
    }
}

// From ScribNibblersEffect
class ZeratulNezarimPrelateFirstEffect extends OneShotEffect {

    public ZeratulNezarimPrelateFirstEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile the top card of target player's library. If it was a creature card, you gain 1 life";
    }

    public ZeratulNezarimPrelateFirstEffect(final ZeratulNezarimPrelateFirstEffect effect) {
        super(effect);
    }

    @Override
    public ZeratulNezarimPrelateFirstEffect copy() {
        return new ZeratulNezarimPrelateFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && targetPlayer.getLibrary().hasCards()) {
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if(card != null) {
                UUID exileId = CardUtil.getExileZoneId(ZeratulNezarimPrelate.EXILE_KEY, game);
                card.moveToExile(exileId, ZeratulNezarimPrelate.EXILE_KEY, source.getSourceId(), game);
                if (card.isCreature()) {
                    you.gainLife(1, game, source);
                    return true;
                }
            }
        }
        return false;
    }
}

// From ChronostutterEffect
class ZeratulNezarimPrelateSecondEffect extends OneShotEffect {

    public ZeratulNezarimPrelateSecondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target nonland permanent into its owner's library second from the top";
    }

    public ZeratulNezarimPrelateSecondEffect(final ZeratulNezarimPrelateSecondEffect effect) {
        super(effect);
    }

    @Override
    public ZeratulNezarimPrelateSecondEffect copy() {
        return new ZeratulNezarimPrelateSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                controller.putCardOnTopXOfLibrary(permanent, game, source, 2);
            }
            return true;
        }
        return false;
    }
}

class ZeratulNezarimPrelateThirdEffect extends OneShotEffect {

    public ZeratulNezarimPrelateThirdEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all other nonland permanents";
    }

    public ZeratulNezarimPrelateThirdEffect(final ZeratulNezarimPrelateThirdEffect effect) {
        super(effect);
    }

    @Override
    public ZeratulNezarimPrelateThirdEffect copy() {
        return new ZeratulNezarimPrelateThirdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            FilterNonlandPermanent filter = new FilterNonlandPermanent();
            filter.add(Predicates.not(new CardIdPredicate(sourcePermanent.getId())));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            Cards toExile = new CardsImpl();
            for (Permanent permanent : permanents) {
                if(permanent != null) {
                    toExile.add(permanent);
                }
            }
            UUID exileId = CardUtil.getExileZoneId(ZeratulNezarimPrelate.EXILE_KEY, game);
            controller.moveCardsToExile(toExile.getCards(game), source, game, false, exileId, CardUtil.createObjectRealtedWindowTitle(source, game, null));
            return true;
        }
        return false;
    }
}