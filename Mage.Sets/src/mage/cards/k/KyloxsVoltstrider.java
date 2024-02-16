package mage.cards.k;

import java.util.UUID;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class KyloxsVoltstrider extends CardImpl {

    public KyloxsVoltstrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Collect evidence 6: Kylox's Voltstrider becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE)
                        .setText("{this} becomes an artifact creature until end of turn"),
                new CollectEvidenceCost(6, true)));

        // Whenever Kylox's Voltstrider attacks, you may cast an instant or sorcery spell from among cards exiled with it.
        // If that spell would be put into a graveyard, put it on the bottom of its owner's library instead.
        this.addAbility(new AttacksTriggeredAbility(new KyloxsVoltstriderEffect(), true));

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private KyloxsVoltstrider(final KyloxsVoltstrider card) {
        super(card);
    }

    @Override
    public KyloxsVoltstrider copy() {
        return new KyloxsVoltstrider(this);
    }
}

class KyloxsVoltstriderEffect extends OneShotEffect {

    KyloxsVoltstriderEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast an instant or sorcery spell from among cards exiled with it. " +
                "If that spell would be put into a graveyard, put it on the bottom of its owner's library instead.";
    }

    private KyloxsVoltstriderEffect(final KyloxsVoltstriderEffect effect) {
        super(effect);
    }

    @Override
    public KyloxsVoltstriderEffect copy() {
        return new KyloxsVoltstriderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(),
                game.getState().getZoneChangeCounter(source.getSourceId()));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (controller == null || exileZone == null) {
            return false;
        }

        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, exileId);
        target.withNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return true;
        }
        controller.chooseTarget(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, false),
                    game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);

            // If that spell would be put into a graveyard, put it on the bottom of its owner's library instead.
            MageObjectReference mor = new MageObjectReference(card, game);
            ContinuousEffect effect = new KyloxsVoltstriderReplacementEffect(mor);
            game.addEffect(effect, source);
        }
        return true;
    }
}

class KyloxsVoltstriderReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    KyloxsVoltstriderReplacementEffect(MageObjectReference mor) {
        super(Duration.Custom, Outcome.Exile);
        staticText = "If that spell would be put into a graveyard, put it on the bottom of its owner's library instead.";
        this.mor = mor;
    }

    private KyloxsVoltstriderReplacementEffect(final KyloxsVoltstriderReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public KyloxsVoltstriderReplacementEffect copy() {
        return new KyloxsVoltstriderReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return event.getTargetId().equals(mor.getSourceId()) &&
                zEvent.getToZone() == Zone.GRAVEYARD &&
                mor.zoneCounterIsCurrent(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        if (card == null || controller == null) {
            return false;
        }

        controller.putCardsOnBottomOfLibrary(card, game, source, false);
        return true;
    }
}
