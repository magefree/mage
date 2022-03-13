package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormHerald extends CardImpl {

    public StormHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Storm Herald enters the battlefield, return any number of Aura cards from your graveyard to the battlefield attached to creatures you control. Exile those Auras at the beginning of your next end step. If those Auras would leave the battlefield, exile them instead of putting them anywhere else.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StormHeraldEffect(), false));

    }

    private StormHerald(final StormHerald card) {
        super(card);
    }

    @Override
    public StormHerald copy() {
        return new StormHerald(this);
    }
}

class StormHeraldEffect extends OneShotEffect {

    public StormHeraldEffect() {
        super(Outcome.Benefit);
        this.staticText = "return any number of Aura cards from your graveyard to the battlefield attached to creatures you control. "
                + "Exile those Auras at the beginning of your next end step. "
                + "If those Auras would leave the battlefield, exile them instead of putting them anywhere else";
    }

    public StormHeraldEffect(final StormHeraldEffect effect) {
        super(effect);
    }

    @Override
    public StormHeraldEffect copy() {
        return new StormHeraldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterCard("aura cards to attach to creatures you control");
            filter.add(SubType.AURA.getPredicate());
            filter.add(new StormHeraldAttachablePredicate(controller.getId()));
            Set<Card> possibleTargets = controller.getGraveyard().getCards(filter, controller.getId(), source, game);
            if (!possibleTargets.isEmpty()) {
                TargetCard targetAuras = new TargetCard(0, Integer.MAX_VALUE, Zone.GRAVEYARD, filter);
                targetAuras.setNotTarget(true);
                controller.chooseTarget(outcome, new CardsImpl(possibleTargets), targetAuras, source, game);

                // Move the cards to the battlefield to a creature you control
                List<Permanent> toExile = new ArrayList<>();
                for (UUID auraId : targetAuras.getTargets()) {
                    Card auraCard = game.getCard(auraId);
                    if (auraCard != null) {
                        FilterPermanent filterAttachTo = new FilterControlledCreaturePermanent("creature you control to attach " + auraCard.getIdName() + " to");
                        filterAttachTo.add(new StormHeraldAttachableToPredicate(auraCard));
                        TargetPermanent targetCreature = new TargetPermanent(filterAttachTo);
                        targetCreature.setNotTarget(true);
                        if (controller.choose(Outcome.PutCardInPlay, targetCreature, source, game)) {
                            Permanent targetPermanent = game.getPermanent(targetCreature.getFirstTarget());
                            if (!targetPermanent.cantBeAttachedBy(auraCard, source, game, true)) {
                                game.getState().setValue("attachTo:" + auraCard.getId(), targetPermanent);
                                controller.moveCards(auraCard, Zone.BATTLEFIELD, source, game);
                                targetPermanent.addAttachment(auraCard.getId(), source, game);
                                Permanent permanent = game.getPermanent(auraId);
                                if (permanent != null) {
                                    toExile.add(permanent);
                                }
                            }
                        }
                    }
                }

                ContinuousEffect continuousEffect = new StormHeraldReplacementEffect();
                continuousEffect.setTargetPointer(new FixedTargets(toExile, game));
                game.addEffect(continuousEffect, source);

                Effect exileEffect = new ExileTargetEffect("exile those Auras");
                exileEffect.setTargetPointer(new FixedTargets(toExile, game));
                game.addDelayedTriggeredAbility(
                        new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            }
            return true;
        }
        return false;
    }
}

class StormHeraldReplacementEffect extends ReplacementEffectImpl {

    StormHeraldReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If those Auras would leave the battlefield, exile them instead of putting them anywhere else";
    }

    StormHeraldReplacementEffect(final StormHeraldReplacementEffect effect) {
        super(effect);
    }

    @Override
    public StormHeraldReplacementEffect copy() {
        return new StormHeraldReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        Permanent aura = ((ZoneChangeEvent) event).getTarget();
        MageObject sourceObject = source.getSourceObject(game);
        if (aura != null && sourceObject != null) {
            game.informPlayers(aura.getLogName() + "goes to exile instead (" + sourceObject.getLogName() + ")");
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED
                && getTargetPointer().getTargets(game, source).contains(event.getTargetId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

class StormHeraldAttachablePredicate implements mage.filter.predicate.Predicate<Card> {

    private final UUID controllerId;

    public StormHeraldAttachablePredicate(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public boolean apply(Card input, Game game) {
        Filter filter;
        for (Target target : input.getSpellAbility().getTargets()) {
            filter = target.getFilter();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game)) {
                if (filter.match(permanent, game)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return "Aura cards that can be attached to controlles creatures";
    }
}

class StormHeraldAttachableToPredicate implements mage.filter.predicate.Predicate<Permanent> {

    private final Card aura;

    public StormHeraldAttachableToPredicate(Card aura) {
        this.aura = aura;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        Filter filter;
        for (Target target : aura.getSpellAbility().getTargets()) {
            filter = target.getFilter();
            if (filter.match(input, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creature that the aura can be attached to";
    }
}
