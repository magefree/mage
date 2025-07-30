package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class HavengulLaboratory extends TransformingDoubleFacedCard {

    public HavengulLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Havengul Mystery",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        this.secondSideCardClazz = mage.cards.h.HavengulMystery.class;

        // {T}: Add {C}.
        this.getLeftHalfCard().addAbility(new ColorlessManaAbility());

        // {4}, {T}: Investigate.
        Ability ability = new SimpleActivatedAbility(new InvestigateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your end step, if you sacrificed three or more Clues this turn, transform Havengul Laboratory.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect(),
                false, HavengulLaboratoryCondition.instance
        ), new HavengulLaboratoryWatcher());

        // When this land transforms into Havengul Mystery, return target creature card from your graveyard to the battlefield.
        ability = new TransformIntoSourceTriggeredAbility(new HavengulMysteryEffect())
                .setTriggerPhrase("When this land transforms into {this}, ");
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(ability);

        // When the creature put onto the battlefield with Havengul Mystery leaves the battlefield, transform Havengul Mystery.
        this.getRightHalfCard().addAbility(new HavengulMysteryLeavesAbility());

        // {T}, Pay 1 life: Add {B}.
        ability = new BlackManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.getRightHalfCard().addAbility(ability);

        this.finalizeDFC();
    }

    private HavengulLaboratory(final HavengulLaboratory card) {
        super(card);
    }

    @Override
    public HavengulLaboratory copy() {
        return new HavengulLaboratory(this);
    }

    static String makeKey(Ability source, Game game) {
        return "HavengulMystery_" + source.getSourceId() + '_' + CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
    }
}

enum HavengulLaboratoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return HavengulLaboratoryWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you sacrificed three or more Clues this turn";
    }
}

class HavengulLaboratoryWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    HavengulLaboratoryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SACRIFICED_PERMANENT) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null || !permanent.hasSubtype(SubType.CLUE, game)) {
            return;
        }
        playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(HavengulLaboratoryWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0) >= 3;
    }
}

class HavengulMysteryEffect extends OneShotEffect {

    HavengulMysteryEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield";
    }

    private HavengulMysteryEffect(final HavengulMysteryEffect effect) {
        super(effect);
    }

    @Override
    public HavengulMysteryEffect copy() {
        return new HavengulMysteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        String key = HavengulLaboratory.makeKey(source, game);
        Set<MageObjectReference> morSet;
        if (game.getState().getValue(key) != null) {
            morSet = (Set<MageObjectReference>) game.getState().getValue(key);
        } else {
            morSet = new HashSet<>();
            game.getState().setValue(key, morSet);
        }
        morSet.add(new MageObjectReference(permanent, game));
        return true;
    }
}

class HavengulMysteryLeavesAbility extends TriggeredAbilityImpl {

    HavengulMysteryLeavesAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
        setLeavesTheBattlefieldTrigger(true);
    }

    private HavengulMysteryLeavesAbility(final HavengulMysteryLeavesAbility ability) {
        super(ability);
    }

    @Override
    public HavengulMysteryLeavesAbility copy() {
        return new HavengulMysteryLeavesAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.BATTLEFIELD) {
            return false;
        }

        String key = HavengulLaboratory.makeKey(this, game);
        Set<MageObjectReference> morSet = (Set<MageObjectReference>) game.getState().getValue(key);
        return morSet != null
                && !morSet.isEmpty()
                && morSet.stream().anyMatch(mor -> mor.refersTo(zEvent.getTarget(), game));
    }

    @Override
    public String getRule() {
        return "When the creature put onto the battlefield with {this} leaves the battlefield, transform {this}.";
    }
}
