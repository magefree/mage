package mage.cards.g;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GroundAssault extends CardImpl {

    public GroundAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Ground Assault deals damage to target creature equal to the number of lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(LandsYouControlCount.instance).setText("{this} deals damage to target creature equal to the number of lands you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private GroundAssault(final GroundAssault card) {
        super(card);
    }

    @Override
    public GroundAssault copy() {
        return new GroundAssault(this);
    }
}
