package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.List;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class ThijarianWitness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackBlockAlonePredicate.instance);
    }

    public ThijarianWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(
                new ExileTargetEffect().setText("if it was attacking or blocking alone, exile it"),
                false, filter, true
        );
        ability.addEffect(new InvestigateEffect().concatBy("and"));
        ability.withFlavorWord("Bear Witness");
        ability.addWatcher(new AttackingBlockingAloneWatcher());

        this.addAbility(ability);
    }

    private ThijarianWitness(final ThijarianWitness card) {
        super(card);
    }

    @Override
    public ThijarianWitness copy() {
        return new ThijarianWitness(this);
    }
}
enum AttackBlockAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return game
                .getState()
                .getWatcher(AttackingBlockingAloneWatcher.class)
                .check(new MageObjectReference(input, game), game);
    }

    @Override
    public String toString() {
        return "attacking or blocking alone";
    }
}
class AttackingBlockingAloneWatcher extends Watcher {
    //records any permanent that is currently attacking or blocking alone
    //clears the result if attacker/blocker count > 1, does NOT clear if count = 0
    //checks on every event while inCombat is true
    private MageObjectReference loneAttacker = null;
    private MageObjectReference loneBlocker = null;
    private boolean inCombat = false;

    public AttackingBlockingAloneWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST
                || event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            loneAttacker = null;
            loneBlocker = null;
            inCombat = false;
            //game.debugMessage("CLEAR");
            return;
        }
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE){
            //game.debugMessage("COMBAT START");
            inCombat = true;
        }
        if (!inCombat) return;
        List<CombatGroup> combat = game.getCombat().getGroups();
        int attackers = 0;
        int blockers = 0;
        for (CombatGroup group : combat){
            attackers += group.getAttackers().size();
            blockers += group.getBlockers().size();
        }
        if (blockers == 1 || attackers == 1){
            for (CombatGroup group : combat){
                if (blockers == 1 && group.getBlockers().size() == 1){
                    loneBlocker = new MageObjectReference(group.getBlockers().get(0), game);
                }
                if (attackers == 1 && group.getAttackers().size() == 1){
                    loneAttacker = new MageObjectReference(group.getAttackers().get(0), game);
                }
            }
        }
        if (attackers > 1){
            loneAttacker = null;
        }
        if (blockers > 1){
            loneBlocker = null;
        }
    }
    public boolean check(MageObjectReference mor, Game game) {
        //MageObjectReference lastMor = new MageObjectReference(mor.getSourceId(), mor.getZoneChangeCounter()-1, game);
        //game.debugMessage("Checking "+mor+" vs "+loneAttacker+", "+loneBlocker);
        return (mor.equals(loneAttacker) || mor.equals(loneBlocker));
    }
}
