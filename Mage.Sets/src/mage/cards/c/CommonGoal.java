package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class CommonGoal extends CardImpl {

    public CommonGoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // One or two target creatures you control each deal damage equal to their power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(1, 2).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent().setTargetTag(3));
    }

    private CommonGoal(final CommonGoal card) {
        super(card);
    }

    @Override
    public CommonGoal copy() {
        return new CommonGoal(this);
    }
}
