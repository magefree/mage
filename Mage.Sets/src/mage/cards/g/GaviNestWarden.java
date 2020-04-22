package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DrawSecondCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DinosaurCatToken;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GaviNestWarden extends CardImpl {

    public GaviNestWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // You may pay {0} rather than pay the cycling cost of the first card you cycle each turn.
        Effect effect = new CyclingZeroCostEffect();
        this.addAbility(new SimpleStaticAbility(effect), new GaviNestWardenWatcher());

        // Whenever you draw your second card each turn, create a 2/2 red and white Dinosaur Cat creature token.
        Ability ability = new DrawSecondCardTriggeredAbility(new CreateTokenEffect(new DinosaurCatToken()), false);
        this.addAbility(ability);
    }

    private GaviNestWarden(final GaviNestWarden card) {
        super(card);
    }

    @Override
    public GaviNestWarden copy() {
        return new GaviNestWarden(this);
    }
}

class GaviNestWardenWatcher extends Watcher {

    private final Map<UUID, Integer> playerCyclingActivations;

    public GaviNestWardenWatcher() {
        super(WatcherScope.GAME);
        playerCyclingActivations = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CYCLED_CARD) {
            playerCyclingActivations.merge(event.getPlayerId(), 1, Integer::sum);
        }
    }

    public int cyclingActivationsThisTurn(UUID playerId) {
        return playerCyclingActivations.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        playerCyclingActivations.clear();
    }
}

class CyclingZeroCostEffect extends CostModificationEffectImpl {


    public CyclingZeroCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.SET_COST);
        staticText = "You may pay {0} rather than pay the cycling cost of the first card you cycle each turn.";
    }

    public CyclingZeroCostEffect(CyclingZeroCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.getManaCostsToPay().clear();
        abilityToModify.getManaCostsToPay().add(new GenericManaCost(0));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        GaviNestWardenWatcher watcher = game.getState().getWatcher(GaviNestWardenWatcher.class);
        return abilityToModify instanceof CyclingAbility
                && watcher != null
                && watcher.cyclingActivationsThisTurn(abilityToModify.getControllerId()) == 0
                && abilityToModify.getControllerId().equals(source.getControllerId());
    }

    @Override
    public CyclingZeroCostEffect copy() {
        return new CyclingZeroCostEffect(this);
    }
}
