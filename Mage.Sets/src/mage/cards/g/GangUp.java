
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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

/**
 *
 * @author TheElk801
 */
public final class GangUp extends CardImpl {

    public GangUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Assist
        this.addAbility(new AssistAbility());

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature with power X or less")));
    }

    public GangUp(final GangUp card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent(new StringBuilder("creature with power ").append(xValue).append(" or less").toString());
            filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }

    @Override
    public GangUp copy() {
        return new GangUp(this);
    }
}
