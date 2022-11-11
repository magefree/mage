
package mage.cards.g;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class GrandWarlordRadha extends CardImpl {

    public GrandWarlordRadha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more creatures you control attack, add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new GrandWarlordRadhaTriggeredAbility(), new CreaturesAttackedWatcher());
    }

    private GrandWarlordRadha(final GrandWarlordRadha card) {
        super(card);
    }

    @Override
    public GrandWarlordRadha copy() {
        return new GrandWarlordRadha(this);
    }
}

class CreaturesAttackedWatcher extends Watcher {

    private final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();

    public CreaturesAttackedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.attackedThisTurnCreatures.clear();
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }
}

class GrandWarlordRadhaTriggeredAbility extends TriggeredAbilityImpl {

    public GrandWarlordRadhaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GrandWarlordRadhaEffect(), false);
        setTriggerPhrase("Whenever one or more creatures you control attack, ");
    }

    public GrandWarlordRadhaTriggeredAbility(final GrandWarlordRadhaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrandWarlordRadhaTriggeredAbility copy() {
        return new GrandWarlordRadhaTriggeredAbility(this);
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
                    && creature.isControlledBy(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

class GrandWarlordRadhaEffect extends OneShotEffect {

    public GrandWarlordRadhaEffect() {
        super(Outcome.Benefit);
        this.staticText = "add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    public GrandWarlordRadhaEffect(final GrandWarlordRadhaEffect effect) {
        super(effect);
    }

    @Override
    public GrandWarlordRadhaEffect copy() {
        return new GrandWarlordRadhaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreaturesAttackedWatcher watcher = game.getState().getWatcher(CreaturesAttackedWatcher.class);
            if (watcher != null) {
                int attackingCreatures = 0;
                for (MageObjectReference attacker : watcher.getAttackedThisTurnCreatures()) {
                    if (attacker.getPermanentOrLKIBattlefield(game).isControlledBy(controller.getId())) {
                        attackingCreatures++;
                    }
                }
                if (attackingCreatures > 0) {
                    Choice manaChoice = new ChoiceImpl();
                    Set<String> choices = new LinkedHashSet<>();
                    choices.add("Red");
                    choices.add("Green");
                    manaChoice.setChoices(choices);
                    manaChoice.setMessage("Select color of mana to add");

                    for (int i = 0; i < attackingCreatures; i++) {
                        Mana mana = new Mana();
                        if (!controller.choose(Outcome.Benefit, manaChoice, game)) {
                            return false;
                        }
                        if (manaChoice.getChoice() == null) {  // can happen if player leaves game
                            return false;
                        }
                        switch (manaChoice.getChoice()) {
                            case "Green":
                                mana.increaseGreen();
                                break;
                            case "Red":
                                mana.increaseRed();
                                break;
                        }
                        controller.getManaPool().addMana(mana, game, source, true);
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}
