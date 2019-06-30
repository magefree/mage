package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GargosViciousWatcher extends CardImpl {

    private static final FilterCard filter = new FilterCard("Hydra spells");

    static {
        filter.add(new SubtypePredicate(SubType.HYDRA));
    }

    public GargosViciousWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Hydra spells you cast cost {4} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 4)));

        // Whenever a creature you control becomes the target of a spell, Gargos, Vicious Watcher fights up to one target creature you don't control.
        this.addAbility(new GargosViciousWatcherTriggeredAbility());
    }

    private GargosViciousWatcher(final GargosViciousWatcher card) {
        super(card);
    }

    @Override
    public GargosViciousWatcher copy() {
        return new GargosViciousWatcher(this);
    }
}

class GargosViciousWatcherTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    GargosViciousWatcherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FightTargetSourceEffect());
        this.addTarget(new TargetPermanent(0, 1, filter, false));
    }

    private GargosViciousWatcherTriggeredAbility(final GargosViciousWatcherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GargosViciousWatcherTriggeredAbility copy() {
        return new GargosViciousWatcherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isControlledBy(this.controllerId)
                || !permanent.isCreature()) {
            return false;
        }
        MageObject object = game.getObject(event.getSourceId());
        return object instanceof Spell;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of a spell, " +
                "{this} fights up to one target creature you don't control.";
    }
}
