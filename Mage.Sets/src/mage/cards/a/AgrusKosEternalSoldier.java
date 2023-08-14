package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AgrusKosEternalSoldier extends CardImpl {

    public AgrusKosEternalSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Agrus Kos, Eternal Soldier becomes the target of an ability that targets only it, you may pay {1}{R/W}. If you do, copy that ability for each other creature you control that ability could target. Each copy targets a different one of those creatures.
        this.addAbility(new AgrusKosEternalSoldierTriggeredAbility());
    }

    private AgrusKosEternalSoldier(final AgrusKosEternalSoldier card) {
        super(card);
    }

    @Override
    public AgrusKosEternalSoldier copy() {
        return new AgrusKosEternalSoldier(this);
    }
}

class AgrusKosEternalSoldierTriggeredAbility extends TriggeredAbilityImpl {

    AgrusKosEternalSoldierTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new AgrusKosEternalSoldierEffect(), new ManaCostsImpl<>("{1}{R/W}")
        ));
        setTriggerPhrase("Whenever {this} becomes the target of an ability that targets only it, ");
    }

    private AgrusKosEternalSoldierTriggeredAbility(final AgrusKosEternalSoldierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AgrusKosEternalSoldierTriggeredAbility copy() {
        return new AgrusKosEternalSoldierTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            return false;
        }
        Set<UUID> targets = stackObject
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (targets.isEmpty() || !targets.stream().allMatch(getSourceId()::equals)) {
            return false;
        }
        this.getEffects().setValue("triggeringAbility", stackObject);
        return true;
    }
}

class AgrusKosEternalSoldierEffect extends CopySpellForEachItCouldTargetEffect {

    AgrusKosEternalSoldierEffect() {
        super();
        staticText = "copy that ability for each other creature you control " +
                "that ability could target. Each copy targets a different one of those creatures";
    }

    @Override
    protected StackObject getStackObject(Game game, Ability source) {
        return (StackObject) getValue("triggeringAbility");
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(p -> !p.equals(permanent))
                .filter(p -> stackObject.canTarget(game, p.getId()))
                .map(p -> new MageObjectReference(p, game))
                .map(MageObjectReferencePredicate::new)
                .collect(Collectors.toList());
    }

    private AgrusKosEternalSoldierEffect(final AgrusKosEternalSoldierEffect effect) {
        super(effect);
    }

    @Override
    public AgrusKosEternalSoldierEffect copy() {
        return new AgrusKosEternalSoldierEffect(this);
    }
}
