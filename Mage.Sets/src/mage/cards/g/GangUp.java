
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GangUp extends CardImpl {

    public GangUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Assist
        this.addAbility(new AssistAbility());

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with power X or less"));
        this.getSpellAbility().setTargetAdjuster(GangUpAdjuster.instance);
    }

    private GangUp(final GangUp card) {
        super(card);
    }

    @Override
    public GangUp copy() {
        return new GangUp(this);
    }
}

enum GangUpAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power " + xValue + " or less");
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}