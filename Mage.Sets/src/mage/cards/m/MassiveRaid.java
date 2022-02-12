package mage.cards.m;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MassiveRaid extends CardImpl {

    public MassiveRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Massive Raid deals damage to any target equal to the number of creatures you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CreaturesYouControlCount.instance).setText("{this} deals damage to any target equal to the number of creatures you control"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private MassiveRaid(final MassiveRaid card) {
        super(card);
    }

    @Override
    public MassiveRaid copy() {
        return new MassiveRaid(this);
    }
}
