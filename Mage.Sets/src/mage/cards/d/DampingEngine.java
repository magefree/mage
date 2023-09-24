package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class DampingEngine extends CardImpl {

    public DampingEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // A player who controls more permanents than each other player can't play lands or cast artifact, creature, or enchantment spells. 
        // That player may sacrifice a permanent for that player to ignore this effect until end of turn.
        Ability ability = new SimpleStaticAbility(new DampingEngineEffect());
        ability.addEffect(new GainAbilitySourceEffect(new DampingEngineSpecialAction()).setText(
                "That player may sacrifice a permanent for that player to ignore this effect until end of turn"
        ));
        this.addAbility(ability);
    }

    private DampingEngine(final DampingEngine card) {
        super(card);
    }

    @Override
    public DampingEngine copy() {
        return new DampingEngine(this);
    }

    static boolean checkPlayer(Player player, Game game) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(player.getId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(
                        Function.identity(),
                        x -> 1,
                        Integer::sum
                ));
        int playerPerms = map.getOrDefault(player.getId(), 0);
        int otherPerms = map
                .entrySet()
                .stream()
                .filter(e -> !player.getId().equals(e.getKey()))
                .mapToInt(e -> e.getValue())
                .max()
                .orElse(0);
        return playerPerms > otherPerms;
    }

    static String makeKey(UUID playerId, Ability source, Game game) {
        return "dampingEngine_"
                + playerId + "_"
                + source.getSourceId() + "_"
                + source.getSourceObjectZoneChangeCounter() + "_"
                + game.getTurnNum();
    }

    static boolean checkValue(UUID playerId, Ability source, Game game) {
        Object object = game.getState().getValue(makeKey(playerId, source, game));
        return object instanceof Boolean && ((Boolean) object);
    }
}

class DampingEngineEffect extends ContinuousRuleModifyingEffectImpl {

    DampingEngineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "A player who controls more permanents than each other player " +
                "can't play lands or cast artifact, creature, or enchantment spells.";
    }

    private DampingEngineEffect(final DampingEngineEffect effect) {
        super(effect);
    }

    @Override
    public DampingEngineEffect copy() {
        return new DampingEngineEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = source.getSourceObject(game);
        if (mageObject != null) {
            return "You can't play the land or cast the spell (" + mageObject.getName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;

    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Permanent dampingEngine = source.getSourcePermanentIfItStillExists(game);
        Card card = game.getCard(event.getSourceId());
        if (player == null || dampingEngine == null || card == null) {
            return false;
        }
        if (!card.isCreature(game)
                && !card.isArtifact(game)
                && !card.isEnchantment(game)
                && !card.isLand(game)) {
            return false;
        }
        return DampingEngine.checkPlayer(player, game)
                && !DampingEngine.checkValue(player.getId(), source, game);
    }
}

class DampingEngineSpecialAction extends SpecialAction {

    DampingEngineSpecialAction() {
        super(Zone.BATTLEFIELD);
        this.addCost(new SacrificeTargetCost(new TargetControlledPermanent(), true));
        this.addEffect(new DampingEngineIgnoreEffect());
        this.setMayActivate(TargetController.ANY);
        this.setRuleVisible(false);
    }

    private DampingEngineSpecialAction(final DampingEngineSpecialAction ability) {
        super(ability);
    }

    @Override
    public DampingEngineSpecialAction copy() {
        return new DampingEngineSpecialAction(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null
                && DampingEngine.checkPlayer(player, game)
                && !DampingEngine.checkValue(player.getId(), this, game)) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule() {
        return "";
    }
}

class DampingEngineIgnoreEffect extends OneShotEffect {

    DampingEngineIgnoreEffect() {
        super(Outcome.AIDontUseIt);
    }

    private DampingEngineIgnoreEffect(final DampingEngineIgnoreEffect effect) {
        super(effect);
    }

    @Override
    public DampingEngineIgnoreEffect copy() {
        return new DampingEngineIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().setValue(DampingEngine.makeKey(source.getControllerId(), source, game), Boolean.TRUE);
        return true;
    }
}
