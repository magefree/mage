package mage.cards.d;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.StackObjectCopyApplier;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class DonalHeraldOfWings extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a nonlegendary creature spell with flying");
    static {
        filterSpell.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filterSpell.add(CardType.CREATURE.getPredicate());
    }

    public DonalHeraldOfWings(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);

        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a nonlegendary creature spell with flying, you may copy it,
        // except the copy is a 1/1 Spirit in addition to its other types.
        // Do this only once each turn. (The copy becomes a token.)
        // TODO: This still triggers and asks if you wanna use it, even if you've used it once this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new DonalHeraldOfWingsEffect(), filterSpell, true, true);
        ability.setIdentifier(MageIdentifier.DonalHeraldOfWingsWatcher);
        this.addAbility(ability, new DonalHeraldOfWingsWatcher());
    }

    private DonalHeraldOfWings(final DonalHeraldOfWings card) { super(card); }

    @Override
    public DonalHeraldOfWings copy() { return new DonalHeraldOfWings(this); }
}

class DonalHeraldOfWingsEffect extends OneShotEffect {

    DonalHeraldOfWingsEffect() {
        super(Outcome.Copy);
        staticText = "you may copy it, except the copy is a 1/1 Spirit in addition to its other types.";
    }

    private DonalHeraldOfWingsEffect(final DonalHeraldOfWingsEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        DonalHeraldOfWingsWatcher watcher = game.getState().getWatcher(DonalHeraldOfWingsWatcher.class);
        if (watcher == null) { return false; }
        if (watcher.wasAbilityUsedThisTurn()) { return false; }

        // Get the card that was cast
        if (this.getTargetPointer() == null) { return false; }
        Spell originalSpell = game.getStack().getSpell(((FixedTarget) this.getTargetPointer()).getTarget());

        // Create a token copy
        // TODO: Copy made is a token, but there's no indication of it on the card
        originalSpell.createCopyOnStack(game, source, controller.getId(), false, 1, DonalHeraldOfWingsApplier.instance);

        return true;
    }

    @Override
    public Effect copy() { return new DonalHeraldOfWingsEffect(this); }
}

enum DonalHeraldOfWingsApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        copiedSpell.addSubType(SubType.SPIRIT);
        // TODO: These two calls don't work
        copiedSpell.getPower().setValue(1);
        copiedSpell.getToughness().setValue(1);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType(int copyNumber) { return null; }
}

class DonalHeraldOfWingsWatcher extends Watcher {

    private boolean abilityUsedThisTurn = false;

    DonalHeraldOfWingsWatcher() { super(WatcherScope.GAME); }

    @Override
    public void watch(GameEvent event, Game game) {
        // get event source. get source's source. check that event's identifier
        if (event.getType() == GameEvent.EventType.COPIED_STACKOBJECT) {
            Object[] stackArray = game.getStack().toArray();

            // If Donal copied a spell, the spell copy will be on top of the stack, and the triggered abiltiy will be just below.
            // The code will still be resolving DonalHeraldOfWingsEffect.
            if (stackArray.length > 1) {
                if (stackArray[1] instanceof StackAbility) {
                    StackAbility stackAbility = (StackAbility) stackArray[1];
                    if (!stackAbility.getAbilities().isEmpty()) {
                        if (stackAbility.getAbilities().get(0).getIdentifier() == MageIdentifier.DonalHeraldOfWingsWatcher) {
                            abilityUsedThisTurn = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsedThisTurn = false;
    }

    boolean wasAbilityUsedThisTurn() { return abilityUsedThisTurn; }
}
