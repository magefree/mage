package mage.cards.m;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MobJustice extends CardImpl {

    public MobJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Mob Justice deals damage to target player equal to the number of creatures you control.
        Effect effect = new DamageTargetEffect(CreaturesYouControlCount.instance);
        effect.setText("{this} deals damage to target player or planeswalker equal to the number of creatures you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private MobJustice(final MobJustice card) {
        super(card);
    }

    @Override
    public MobJustice copy() {
        return new MobJustice(this);
    }
}
