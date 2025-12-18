package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestPlayBlitzball extends TransformingDoubleFacedCard {

    public SidequestPlayBlitzball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{R}",
                "World Champion, Celestial Weapon",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "R"
        );

        // Sidequest: Play Blitzball
        // At the beginning of combat on your turn, target creature you control gets +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // At the end of combat on your turn, if a player was dealt 6 or more combat damage this turn, transform this enchantment, then attach it to a creature you control.
        ability = new EndOfCombatTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, false
        ).withInterveningIf(SidequestPlayBlitzballCondition.instance).setTriggerPhrase("At the end of combat on your turn, ");
        ability.addEffect(new SidequestPlayBlitzballEffect());
        ability.addWatcher(new SidequestPlayBlitzballWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // World Champion, Celestial Weapon
        // Double Overdrive -- Equipped creature gets +2/+0 and has double strike.
        Ability eAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        eAbility.addEffect(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has double strike"));
        this.getRightHalfCard().addAbility(eAbility.withFlavorWord("Double Overdrive"));

        // Equip {3}
        this.getRightHalfCard().addAbility(new EquipAbility(3));
    }

    private SidequestPlayBlitzball(final SidequestPlayBlitzball card) {
        super(card);
    }

    @Override
    public SidequestPlayBlitzball copy() {
        return new SidequestPlayBlitzball(this);
    }
}

enum SidequestPlayBlitzballCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SidequestPlayBlitzballWatcher.checkPlayers(game, source);
    }

    @Override
    public String toString() {
        return "a player was dealt 6 or more combat damage this turn";
    }
}

class SidequestPlayBlitzballEffect extends OneShotEffect {

    SidequestPlayBlitzballEffect() {
        super(Outcome.Benefit);
        staticText = ", then attach it to a creature you control";
    }

    private SidequestPlayBlitzballEffect(final SidequestPlayBlitzballEffect effect) {
        super(effect);
    }

    @Override
    public SidequestPlayBlitzballEffect copy() {
        return new SidequestPlayBlitzballEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        return Optional
                .ofNullable(target.getFirstTarget())
                .map(game::getPermanent)
                .map(p -> p.addAttachment(p.getId(), source, game))
                .orElse(false);
    }
}

class SidequestPlayBlitzballWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    SidequestPlayBlitzballWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && ((DamagedEvent) event).isCombatDamage()) {
            map.compute(event.getTargetId(), (u, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount()));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    private boolean checkMap(UUID playerId) {
        return map.getOrDefault(playerId, 0) >= 6;
    }

    static boolean checkPlayers(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(game.getState().getWatcher(SidequestPlayBlitzballWatcher.class)::checkMap);
    }
}
