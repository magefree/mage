package mage.cards.u;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class UltimateAlliance extends CardImpl {

    public UltimateAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Ultimate Alliance deals damage equal to the number of creatures you control to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CreaturesYouControlCount.PLURAL)
            .setText("{this} deals damage equal to the number of creatures you control to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private UltimateAlliance(final UltimateAlliance card) {
        super(card);
    }

    @Override
    public UltimateAlliance copy() {
        return new UltimateAlliance(this);
    }
}
