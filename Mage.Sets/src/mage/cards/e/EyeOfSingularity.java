
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class EyeOfSingularity extends CardImpl {

    public EyeOfSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.WORLD);

        // When Eye of Singularity enters the battlefield, destroy each permanent with the same name as another permanent, except for basic lands. They can't be regenerated.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EyeOfSingularityETBEffect()));

        // Whenever a permanent other than a basic land enters the battlefield, destroy all other permanents with that name. They can't be regenerated.
        this.addAbility(new EyeOfSingularityTriggeredAbility());
    }

    private EyeOfSingularity(final EyeOfSingularity card) {
        super(card);
    }

    @Override
    public EyeOfSingularity copy() {
        return new EyeOfSingularity(this);
    }
}

class EyeOfSingularityETBEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    EyeOfSingularityETBEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy each permanent with the same name as another permanent, except for basic lands. They can't be regenerated";
    }

    EyeOfSingularityETBEffect(final EyeOfSingularityETBEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfSingularityETBEffect copy() {
        return new EyeOfSingularityETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<String, UUID> cardNames = new HashMap<>();
        Map<UUID, Integer> toDestroy = new HashMap<>();

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            String cardName = permanent.getName();
            if (cardNames.get(cardName) == null) {
                cardNames.put(cardName, permanent.getId());
            } else {
                toDestroy.put(cardNames.get(cardName), 1);
                toDestroy.put(permanent.getId(), 1);
            }
        }
        for (UUID id : toDestroy.keySet()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}

class EyeOfSingularityTriggeredAbility extends TriggeredAbilityImpl {

    EyeOfSingularityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EyeOfSingularityTriggeredEffect(), false);
    }

    EyeOfSingularityTriggeredAbility(final EyeOfSingularityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EyeOfSingularityTriggeredAbility copy() {
        return new EyeOfSingularityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);

        if (event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        if (permanent != null && !permanent.isBasic(game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent other than a basic land enters the battlefield, destroy all other permanents with that name. They can't be regenerated.";
    }
}

class EyeOfSingularityTriggeredEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    EyeOfSingularityTriggeredEffect() {
        super(Outcome.DestroyPermanent);
    }

    EyeOfSingularityTriggeredEffect(final EyeOfSingularityTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> toDestroy = new HashMap<>();
        Permanent etbPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);

        if (etbPermanent == null) {
            return false;
        }
        String cn = etbPermanent.getName();

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            String cardName = permanent.getName();
            if (cardName.equals(cn) && !Objects.equals(permanent.getId(), etbPermanent.getId())) {
                toDestroy.put(permanent.getId(), 1);
            }
        }

        for (UUID id : toDestroy.keySet()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                permanent.destroy(source, game, false);
            }
        }

        return true;
    }

    @Override
    public EyeOfSingularityTriggeredEffect copy() {
        return new EyeOfSingularityTriggeredEffect(this);
    }
}
