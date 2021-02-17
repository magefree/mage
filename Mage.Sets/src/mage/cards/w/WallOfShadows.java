
package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WallOfShadows extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("spells that can target only Walls or of abilities that can target only Walls");

    static {
        filter.add(CanTargetOnlyWallsPredicate.instance);
    }

    public WallOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Prevent all damage that would be dealt to Wall of Vapor by creatures it's blocking.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WallOfShadowsEffect()));

        // Wall of Shadows can't be the target of spells that can target only Walls or of abilities that can target only Walls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private WallOfShadows(final WallOfShadows card) {
        super(card);
    }

    @Override
    public WallOfShadows copy() {
        return new WallOfShadows(this);
    }
}

class WallOfShadowsEffect extends PreventionEffectImpl {

    WallOfShadowsEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to {this} by creatures it's blocking";
    }

    private WallOfShadowsEffect(final WallOfShadowsEffect effect) {
        super(effect);
    }

    @Override
    public WallOfShadowsEffect copy() {
        return new WallOfShadowsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)
                || !(event instanceof DamageEvent)
                || event.getAmount() <= 0) {
            return false;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        if (!event.getTargetId().equals(source.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new BlockedByIdPredicate(source.getSourceId()));
        return permanent != null && filter.match(permanent, game);
    }
}

enum CanTargetOnlyWallsPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        StackObject stackObject = game.getStack().getSpell(input.getId());
        if (stackObject == null) {
            return false;
        }
        boolean canTargetOnlyWalls = false;
        for (Mode mode : stackObject.getStackAbility().getModes().values()) {
            for (Target target : mode.getTargets()) {
                Filter filter = target.getFilter();
                if (!(filter instanceof FilterPermanent)) {
                    return false; // can target non-permanents (i.e. not Walls)
                }
                for (Object predicate : filter.getPredicates()) {
                    if (predicate instanceof SubType.SubTypePredicate) {
                        if (predicate.toString().equals("Subtype(Wall)")) {
                            canTargetOnlyWalls = true; // can target a Wall
                        } else {
                            return false; // can target a non-Wall permanent
                        }
                    }
                    // no return statement here, as different predicates might still apply (e.g. "blocking Wall")
                }
            }
        }
        return canTargetOnlyWalls;
    }

    @Override
    public String toString() {
        return "can target only Walls";
    }
}
