package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class Raven extends CardImpl {

    public Raven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent you control is turned face down, you gain 1 life.
        this.addAbility(new TurnedFaceDownAllTriggeredAbility(new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_PERMANENT));

        // {3}, {T}: Turn target permanent you control face down.
        Ability ability = new SimpleActivatedAbility(new RavenEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    public Raven(final Raven card) {
        super(card);
    }

    @Override
    public Raven copy() {
        return new Raven(this);
    }
}

class RavenEffect extends OneShotEffect {

    RavenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn target permanent you control face down.";
    }

    RavenEffect(final RavenEffect effect) {
        super(effect);
    }

    @Override
    public RavenEffect copy() {
        return new RavenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}

// Modified TurnedFaceUpAllTriggeredAbility
class TurnedFaceDownAllTriggeredAbility extends TriggeredAbilityImpl {

    private FilterPermanent filter;
    private boolean setTargetPointer;

    public TurnedFaceDownAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public TurnedFaceDownAllTriggeredAbility(Effect effect, FilterPermanent filter,  boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, false);
    }

    public TurnedFaceDownAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        // has to be set so the ability triggers if card itself is turn faced down
        this.setWorksFaceDown(true);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TurnedFaceDownAllTriggeredAbility(final TurnedFaceDownAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
    }

    @Override
    public TurnedFaceDownAllTriggeredAbility copy() {
        return new TurnedFaceDownAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEDOWN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            MageObject sourceObj = this.getSourceObject(game);
            if (sourceObj != null) {
                if (sourceObj instanceof Card && !((Card)sourceObj).isFaceDown(game)) {
                    // if face up and it's not itself that is turned face down, it does not trigger
                    return false;
                }
            } else {
                // Permanent is and was not on the battlefield
                return false;
            }
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                for (Effect effect: getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " is turned face down, " + super.getRule();
    }
}

