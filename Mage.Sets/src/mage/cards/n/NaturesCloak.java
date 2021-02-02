
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class NaturesCloak extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Green creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NaturesCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Green creatures you control gain forestwalk until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new ForestwalkAbility(false), Duration.EndOfTurn, filter));
    }

    private NaturesCloak(final NaturesCloak card) {
        super(card);
    }

    @Override
    public NaturesCloak copy() {
        return new NaturesCloak(this);
    }
}
