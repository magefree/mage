
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MercadiasDownfall extends CardImpl {
    
    private static String rule = "Each attacking creature gets +1/+0 until end of turn for each nonbasic land defending player controls";

    public MercadiasDownfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Each attacking creature gets +1/+0 until end of turn for each nonbasic land defending player controls.
        this.getSpellAbility().addEffect(new BoostAllEffect(new DefendersNonBasicLandCount(), StaticValue.get(0), Duration.EndOfTurn, new FilterAttackingCreature(), true, rule));

    }

    private MercadiasDownfall(final MercadiasDownfall card) {
        super(card);
    }

    @Override
    public MercadiasDownfall copy() {
        return new MercadiasDownfall(this);
    }

    static class DefendersNonBasicLandCount implements DynamicValue {
        
        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            UUID defenderId;
            for (CombatGroup group : game.getCombat().getGroups()) {
                defenderId = group.getDefenderId();
                if (group.isDefenderIsPlaneswalker()) {
                    Permanent permanent = game.getPermanent(defenderId);
                    if (permanent != null) {
                        defenderId = permanent.getControllerId();
                    }
                }
                FilterLandPermanent filter = new FilterLandPermanent("nonbasic land");
                filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
                System.out.println("The number of nonbasic lands is " + game.getBattlefield().countAll(filter, defenderId, game));
                return game.getBattlefield().countAll(filter, defenderId, game);
            }
            return 0;
        }

        @Override
        public DefendersNonBasicLandCount copy() {
            return new DefendersNonBasicLandCount();
        }

        @Override
        public String toString() {
            return "X";
        }

        @Override
        public String getMessage() {
            return "the number of nonbasic lands defending player controls";
        }
    }

}
