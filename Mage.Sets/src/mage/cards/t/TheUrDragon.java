
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class TheUrDragon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public TheUrDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // <i>Eminence</i> &mdash; As long as The Ur-Dragon is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("As long as {this} is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);
        effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("");
        ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield
        this.addAbility(new TheUrDragonTriggeredAbility(), new DragonsAttackedWatcher());
    }

    public TheUrDragon(final TheUrDragon card) {
        super(card);
    }

    @Override
    public TheUrDragon copy() {
        return new TheUrDragon(this);
    }
}

class DragonsAttackedWatcher extends Watcher {

    public final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();

    public DragonsAttackedWatcher() {
        super(DragonsAttackedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public DragonsAttackedWatcher(final DragonsAttackedWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.attackedThisTurnCreatures.clear();
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            if (game.getPermanent(event.getSourceId()).hasSubtype(SubType.DRAGON, game)) {
                this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
            }
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    @Override
    public DragonsAttackedWatcher copy() {
        return new DragonsAttackedWatcher(this);
    }

}

class TheUrDragonTriggeredAbility extends TriggeredAbilityImpl {

    public TheUrDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheUrDragonEffect(), false);
    }

    public TheUrDragonTriggeredAbility(final TheUrDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheUrDragonTriggeredAbility copy() {
        return new TheUrDragonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && creature.getControllerId() != null
                    && creature.getControllerId().equals(this.getControllerId())
                    && creature.hasSubtype(SubType.DRAGON, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Dragons you control attack, " + super.getRule();
    }
}

class TheUrDragonEffect extends OneShotEffect {

    public TheUrDragonEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw that many cards, then you may put a permanent card from your hand onto the battlefield";
    }

    public TheUrDragonEffect(final TheUrDragonEffect effect) {
        super(effect);
    }

    @Override
    public TheUrDragonEffect copy() {
        return new TheUrDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DragonsAttackedWatcher watcher = (DragonsAttackedWatcher) game.getState().getWatchers().get(DragonsAttackedWatcher.class.getSimpleName());
            if (watcher != null) {
                int attackingDragons = 0;
                for (MageObjectReference attacker : watcher.getAttackedThisTurnCreatures()) {
                    if (attacker.getPermanentOrLKIBattlefield(game).getControllerId().equals(controller.getId())) {
                        attackingDragons++;
                    }
                }
                if (attackingDragons > 0) {
                    controller.drawCards(attackingDragons, game);
                }
                return new PutCardFromHandOntoBattlefieldEffect().apply(game, source);
            }
        }
        return false;
    }
}
