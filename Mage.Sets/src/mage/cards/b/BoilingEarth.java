
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class BoilingEarth extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BoilingEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Boiling Earth deals 1 damage to each creature your opponents control.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));

        // Awaken 4—{6}{R}
        this.addAbility(new AwakenAbility(this, 4, "{6}{R}"));
    }

    public BoilingEarth(final BoilingEarth card) {
        super(card);
    }

    @Override
    public BoilingEarth copy() {
        return new BoilingEarth(this);
    }
}
