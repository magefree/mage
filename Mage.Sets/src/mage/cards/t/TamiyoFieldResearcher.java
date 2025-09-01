package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TamiyoFieldResearcherEmblem;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TamiyoFieldResearcher extends CardImpl {

    public TamiyoFieldResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);

        this.setStartingLoyalty(4);

        // +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
        Ability ability = new LoyaltyAbility(new TamiyoFieldResearcherEffect1(), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // -2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
        ability = new LoyaltyAbility(new TapTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENTS_NON_LAND, false));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("They"));
        this.addAbility(ability);

        // -7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(3), -7);
        ability.addEffect(new GetEmblemEffect(new TamiyoFieldResearcherEmblem()));
        this.addAbility(ability);
    }

    private TamiyoFieldResearcher(final TamiyoFieldResearcher card) {
        super(card);
    }

    @Override
    public TamiyoFieldResearcher copy() {
        return new TamiyoFieldResearcher(this);
    }
}

class TamiyoFieldResearcherEffect1 extends OneShotEffect {

    public TamiyoFieldResearcherEffect1() {
        super(Outcome.PreventDamage);
        this.staticText = "Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card";
    }

    private TamiyoFieldResearcherEffect1(final TamiyoFieldResearcherEffect1 effect) {
        super(effect);
    }

    @Override
    public TamiyoFieldResearcherEffect1 copy() {
        return new TamiyoFieldResearcherEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<MageObjectReference> creatures = new ArrayList<>();
            for (UUID uuid : getTargetPointer().getTargets(game, source)) {
                creatures.add(new MageObjectReference(uuid, game));
            }
            if (!creatures.isEmpty()) {
                DelayedTriggeredAbility delayedAbility = new TamiyoFieldResearcherDelayedTriggeredAbility(creatures, game.getTurnNum());
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}

// batch per source:
// > If Tamiyo’s first ability targets two creatures, and both deal combat damage at the same time, the delayed triggered ability triggers twice.
// > (2016-08-23)
class TamiyoFieldResearcherDelayedTriggeredAbility extends DelayedTriggeredAbility implements BatchTriggeredAbility<DamagedEvent> {

    private final int startingTurn;
    private final List<MageObjectReference> creatures;

    TamiyoFieldResearcherDelayedTriggeredAbility(List<MageObjectReference> creatures, int startingTurn) {
        super(new DrawCardSourceControllerEffect(1, true), Duration.Custom, false);
        this.creatures = creatures;
        this.startingTurn = startingTurn;
        setTriggerPhrase("Until your next turn, whenever either of those creatures deals combat damage, ");
    }

    private TamiyoFieldResearcherDelayedTriggeredAbility(final TamiyoFieldResearcherDelayedTriggeredAbility ability) {
        super(ability);
        this.creatures = ability.creatures;
        this.startingTurn = ability.startingTurn;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkEvent(DamagedEvent event, Game game) {
        if (!event.isCombatDamage() || event.getAmount() <= 0) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null) {
            return false;
        }
        return creatures.contains(new MageObjectReference(permanent.getId(), game));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((DamagedBatchBySourceEvent) event, game).isEmpty();
    }

    @Override
    public boolean isInactive(Game game) {
        return game.isActivePlayer(getControllerId()) && game.getTurnNum() != startingTurn;
    }

    @Override
    public TamiyoFieldResearcherDelayedTriggeredAbility copy() {
        return new TamiyoFieldResearcherDelayedTriggeredAbility(this);
    }

}
