
package mage.cards.l;

import java.util.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author maurer.it_at_gmail.com, dustinconrad
 */
public final class LeoninArbiter extends CardImpl {

    private static final String KEY_STRING = "_ignoreEffectForTurn";

    public LeoninArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Players can't search libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeoninArbiterCantSearchEffect(KEY_STRING)));

        //  Any player may pay {2} for that player to ignore this effect until end of turn.
        this.addAbility(new LeoninArbiterSpecialAction(KEY_STRING));
    }

    private LeoninArbiter(final LeoninArbiter card) {
        super(card);
    }

    @Override
    public LeoninArbiter copy() {
        return new LeoninArbiter(this);
    }
}

class LeoninArbiterSpecialAction extends SpecialAction {

    public LeoninArbiterSpecialAction(final String keyString) {
        super(Zone.BATTLEFIELD);
        this.addCost(new ManaCostsImpl<>("{2}"));
        this.addEffect(new LeoninArbiterIgnoreEffect(keyString));
        this.setMayActivate(TargetController.ANY);
    }

    public LeoninArbiterSpecialAction(final LeoninArbiterSpecialAction ability) {
        super(ability);
    }

    @Override
    public LeoninArbiterSpecialAction copy() {
        return new LeoninArbiterSpecialAction(this);
    }
}

class LeoninArbiterIgnoreEffect extends OneShotEffect {

    private final String keyString;

    public LeoninArbiterIgnoreEffect(final String keyString) {
        super(Outcome.Benefit);
        this.keyString = keyString;
        this.staticText = "Any player may pay {2} for that player to ignore this effect until end of turn";
    }

    public LeoninArbiterIgnoreEffect(final LeoninArbiterIgnoreEffect effect) {
        super(effect);
        this.keyString = effect.keyString;
    }

    @Override
    public LeoninArbiterIgnoreEffect copy() {
        return new LeoninArbiterIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        String key = permanent.getId() + keyString;

        // Using a Map.Entry since there is no pair class
        long zoneChangeCount = permanent.getZoneChangeCounter(game);
        long turnNum = game.getTurnNum();
        Long activationState = zoneChangeCount << 32 | turnNum & 0xFFFFFFFFL;

        Map.Entry<Long, Set<UUID>> turnIgnoringPlayersPair = (Map.Entry<Long, Set<UUID>>) game.getState().getValue(key);
        if (turnIgnoringPlayersPair == null || !activationState.equals(turnIgnoringPlayersPair.getKey())) {
            turnIgnoringPlayersPair = new AbstractMap.SimpleImmutableEntry<>(activationState, new HashSet<>());
            game.getState().setValue(key, turnIgnoringPlayersPair);
        }

        turnIgnoringPlayersPair.getValue().add(game.getPriorityPlayerId());
        return true;
    }
}

class LeoninArbiterCantSearchEffect extends ContinuousRuleModifyingEffectImpl {

    private final String keyString;

    public LeoninArbiterCantSearchEffect(final String keyString) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Players can't search libraries.";
        this.keyString = keyString;
    }

    public LeoninArbiterCantSearchEffect(LeoninArbiterCantSearchEffect effect) {
        super(effect);
        this.keyString = effect.keyString;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.SEARCH_LIBRARY == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            boolean applies = true;
            String key = permanent.getId() + keyString;
            Map.Entry<Long, Set<UUID>> turnIgnoringPlayersPair = (Map.Entry<Long, Set<UUID>>) game.getState().getValue(key);
            if (turnIgnoringPlayersPair != null) {
                long zoneChangeCount = permanent.getZoneChangeCounter(game);
                long turnNum = game.getTurnNum();
                Long activationState = zoneChangeCount << 32 | turnNum & 0xFFFFFFFFL;
                if (activationState.equals(turnIgnoringPlayersPair.getKey())) {
                    applies = !turnIgnoringPlayersPair.getValue().contains(event.getPlayerId());
                }
            }
            return applies;
        }
        return false;
    }

    @Override
    public LeoninArbiterCantSearchEffect copy() {
        return new LeoninArbiterCantSearchEffect(this);
    }
}
