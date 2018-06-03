
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HungerOfTheNim extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public HungerOfTheNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target creature gets +1/+0 until end of turn for each artifact you control.
        Effect effect = new BoostTargetEffect(new PermanentsOnBattlefieldCount(filter), new StaticValue(0), Duration.EndOfTurn, true);
        effect.setText("Target creature gets +1/+0 until end of turn for each artifact you control");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public HungerOfTheNim(final HungerOfTheNim card) {
        super(card);
    }

    @Override
    public HungerOfTheNim copy() {
        return new HungerOfTheNim(this);
    }
}
