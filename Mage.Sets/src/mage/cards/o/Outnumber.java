package mage.cards.o;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Outnumber extends CardImpl {

    public Outnumber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Outnumber deals damage to target creature equal to the number of creatures you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent("the number of creatures you control")));
        effect.setText("{this} deals damage to target creature equal to the number of creatures you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

    }

    private Outnumber(final Outnumber card) {
        super(card);
    }

    @Override
    public Outnumber copy() {
        return new Outnumber(this);
    }
}
