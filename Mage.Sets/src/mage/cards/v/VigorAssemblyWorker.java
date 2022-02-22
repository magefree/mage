package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author mschatz
 */
public final class VigorAssemblyWorker extends CardImpl {

    public VigorAssemblyWorker(UUID ownerId, CardSetInfo setInfo) {
    super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
    this.subtype.add(SubType.ELEMENTAL);
    this.subtype.add(SubType.INCARNATION);
    this.subtype.add(SubType.ASSEMBLY_WORKER); // Apply Olivia Voldaren

    this.power = new MageInt(6);
    this.toughness = new MageInt(6);

    // Apply Prismatic Lace
    this.color.setBlack(true);
    this.color.setGreen(true);
    this.color.setRed(true);
    this.color.setWhite(true);

    // Trample
    this.addAbility(TrampleAbility.getInstance());

    // If damage would be dealt to a creature you control other than Vigor, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way.
    this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VigorAssemblyWorkerReplacementEffect()));

    // When Vigor is put into a graveyard from anywhere, shuffle it into its owner's library.
    this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private VigorAssemblyWorker(final VigorAssemblyWorker card) {
        super(card);
    }

    @Override
    public VigorAssemblyWorker copy() {
        return new VigorAssemblyWorker(this);
    }
}

class VigorAssemblyWorkerReplacementEffect extends ReplacementEffectImpl {

    VigorAssemblyWorkerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "If damage would be dealt to a creature you control other than {this}, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way";
    }

    VigorAssemblyWorkerReplacementEffect(final VigorAssemblyWorkerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int preventedDamage = event.getAmount();
            event.setAmount(0);
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(preventedDamage), source.getControllerId(), source, game);
            }
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), preventedDamage));
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public VigorAssemblyWorkerReplacementEffect copy() {
        return new VigorAssemblyWorkerReplacementEffect(this);
    }
}
