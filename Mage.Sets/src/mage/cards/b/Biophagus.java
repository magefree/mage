package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Biophagus extends CardImpl {

    public Biophagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN, SubType.TYRANID, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Genomic Enhancement — {T}: Add one mana of any color. If this mana is spent to cast a creature spell,
        // that creature enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new AnyColorManaAbility(new TapSourceCost(), true).withFlavorWord("Genomic Enhancement");
        ability.getEffects().get(0).setText("Add one mana of any color. If this mana is spent to cast a creature spell, " +
                "that creature enters the battlefield with an additional +1/+1 counter on it.");
        this.addAbility(ability, new BiophagusWatcher(ability));
    }

    private Biophagus(final Biophagus card) {
        super(card);
    }

    @Override
    public Biophagus copy() {
        return new Biophagus(this);
    }
}

class BiophagusWatcher extends Watcher {

    private final Ability source;

    BiophagusWatcher(Ability source) {
        super(WatcherScope.CARD);
        this.source = source;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            MageObject target = game.getObject(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game)
                    && event.getFlag()) {
                if (target instanceof Spell) {
                    game.getState().addEffect(new BiophagusEntersBattlefieldEffect(
                            new MageObjectReference(((Spell) target).getSourceId(), target.getZoneChangeCounter(game), game)), source);
                }
            }
        }
    }
}

class BiophagusEntersBattlefieldEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    public BiophagusEntersBattlefieldEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "If that mana is spent on a multicolored creature spell, that creature enters the battlefield with an additional +1/+1 counter on it";
        this.mor = mor;
    }

    public BiophagusEntersBattlefieldEffect(BiophagusEntersBattlefieldEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && mor.refersTo(permanent, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public BiophagusEntersBattlefieldEffect copy() {
        return new BiophagusEntersBattlefieldEffect(this);
    }
}
