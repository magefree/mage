package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.DealtDamageThisGameWatcher;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RuricTharMagecrusher extends CardImpl {

    public RuricTharMagecrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ruric Thar has hexproof as long as they haven't dealt combat damage yet.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HexproofAbility.getInstance()),
            RuricTharMagecrusherCondition.instance,
            "{this} has hexproof as long as they haven't dealt combat damage yet"
        )).addHint(RuricTharMagecrusherCondition.getHint()), new RuricTharMagecrusherWatcher());
    }

    private RuricTharMagecrusher(final RuricTharMagecrusher card) {
        super(card);
    }

    @Override
    public RuricTharMagecrusher copy() {
        return new RuricTharMagecrusher(this);
    }
}

enum RuricTharMagecrusherCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
        instance, "This creature hasn't dealt combat damage yet this game"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return RuricTharMagecrusherWatcher.checkCreature(game, source);
    }

    @Override
    public String toString() {
        return "{this} hasn't dealt combat damage yet";
    }
}

class RuricTharMagecrusherWatcher extends Watcher {

    private final Set<MageObjectReference> damagers = new HashSet<>();

    public RuricTharMagecrusherWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                // keep the stored values from getting too big, especially since it doesn't reset between games
                damagers.removeIf(mor -> !mor.zoneCounterIsCurrent(game));
                return;
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER:
                if (!((DamagedEvent) event).isCombatDamage()) {
                    return;
                } else {
                    break;
                }
            default:
                return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            damagers.add(new MageObjectReference(permanent, game));
        }
    }

    public static boolean checkCreature(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(RuricTharMagecrusherWatcher.class)
                .damagers
                .stream()
                .noneMatch(mor -> mor.refersTo(source.getSourcePermanentOrLKI(game), game));
    }
}
