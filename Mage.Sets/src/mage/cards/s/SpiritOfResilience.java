package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author muz
 */
public final class SpiritOfResilience extends CardImpl {

    public SpiritOfResilience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on this creature, then you may have this creature become a copy of an artifact or creature card from among those cards until end of turn.
        this.addAbility(new SpiritOfResilienceTrigger());
    }

    private SpiritOfResilience(final SpiritOfResilience card) {
        super(card);
    }

    @Override
    public SpiritOfResilience copy() {
        return new SpiritOfResilience(this);
    }
}

class SpiritOfResilienceTrigger extends TriggeredAbilityImpl {

    SpiritOfResilienceTrigger() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        this.addEffect(new SpiritOfResilienceCopyEffect());
        setTriggerPhrase("Whenever one or more cards leave your graveyard, ");
    }

    private SpiritOfResilienceTrigger(final SpiritOfResilienceTrigger ability) {
        super(ability);
    }

    @Override
    public SpiritOfResilienceTrigger copy() {
        return new SpiritOfResilienceTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent == null || zEvent.getFromZone() != Zone.GRAVEYARD || zEvent.getCards() == null) {
            return false;
        }

        List<UUID> leavingIds = zEvent.getCards().stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isOwnedBy(getControllerId()))
                .map(Card::getId)
                .collect(Collectors.toList());
        if (leavingIds.isEmpty()) {
            return false;
        }

        getEffects().setValue("leavingCardIds", leavingIds);
        return true;
    }
}

class SpiritOfResilienceCopyEffect extends OneShotEffect {

    SpiritOfResilienceCopyEffect() {
        super(Outcome.Copy);
        staticText = ", then you may have this creature become a copy of an artifact or creature card from among those cards until end of turn";
    }

    private SpiritOfResilienceCopyEffect(final SpiritOfResilienceCopyEffect effect) {
        super(effect);
    }

    @Override
    public SpiritOfResilienceCopyEffect copy() {
        return new SpiritOfResilienceCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || sourcePermanent == null) {
            return false;
        }

        List<UUID> leavingIds = (List<UUID>) getValue("leavingCardIds");
        if (leavingIds == null || leavingIds.isEmpty()) {
            return false;
        }

        Cards candidates = new CardsImpl();
        for (UUID id : leavingIds) {
            Card card = game.getCard(id);
            if (card != null && StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE.match(card, controller.getId(), source, game)) {
                candidates.add(id);
            }
        }
        if (candidates.isEmpty()) {
            return false;
        }

        if (!controller.chooseUse(outcome, "Have " + sourcePermanent.getLogName()
                + " become a copy of an artifact or creature card from among those cards?", source, game)) {
            return false;
        }

        TargetCard target = new TargetCard(1, 1, Zone.ALL, StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE);
        target.withNotTarget(true);
        controller.choose(outcome, candidates, target, source, game);

        Card copyFrom = game.getCard(target.getFirstTarget());
        if (copyFrom == null) {
            return false;
        }

        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, copyFrom, sourcePermanent.getId());
        game.addEffect(copyEffect, source);
        game.informPlayers(controller.getLogName() + " has " + sourcePermanent.getLogName()
                + " become a copy of " + copyFrom.getName() + " until end of turn.");
        return true;
    }
}
