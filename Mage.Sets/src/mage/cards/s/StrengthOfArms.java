
package mage.cards.s;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class StrengthOfArms extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("If you control an Equipment,");
    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public StrengthOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature gets +2/+2 until end of turn.
        // If you control an Equipment, create a 1/1 white Human Soldier creature token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new HumanSoldierToken()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "If you control an Equipment, create a 1/1 white Human Soldier creature token."));
    }

    private StrengthOfArms(final StrengthOfArms card) {
        super(card);
    }

    @Override
    public StrengthOfArms copy() {
        return new StrengthOfArms(this);
    }
}
