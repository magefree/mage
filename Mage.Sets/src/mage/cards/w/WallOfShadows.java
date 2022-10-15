
package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.Filter;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WallOfShadows extends CardImpl {

    private static final FilterPermanent filterPrevent = new FilterCreaturePermanent("creatures it's blocking");
    private static final FilterObject filterCantTarget = new FilterStackObject("spells that can target only Walls or of abilities that can target only Walls");

    static {
        filterPrevent.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
        filterCantTarget.add(CanTargetOnlyWallsPredicate.instance);
    }

    public WallOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Prevent all damage that would be dealt to Wall of Vapor by creatures it's blocking.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filterPrevent)));

        // Wall of Shadows can't be the target of spells that can target only Walls or of abilities that can target only Walls.
        this.addAbility(new SimpleStaticAbility(new CantBeTargetedSourceEffect(filterCantTarget, Duration.WhileOnBattlefield)));
    }

    private WallOfShadows(final WallOfShadows card) {
        super(card);
    }

    @Override
    public WallOfShadows copy() {
        return new WallOfShadows(this);
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
