
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;


/**
 *
 * @author North
 */
public final class Mutilate extends CardImpl {

    private static final String ruleText = "All creatures get -1/-1 until end of turn for each Swamp you control";

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Mutilate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // All creatures get -1/-1 until end of turn for each Swamp you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter, -1);
        ContinuousEffect effect = new BoostAllEffect(count, count, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false, null, true);
        effect.overrideRuleText(ruleText);
        this.getSpellAbility().addEffect(effect);
    }

    public Mutilate(final Mutilate card) {
        super(card);
    }

    @Override
    public Mutilate copy() {
        return new Mutilate(this);
    }
}
