package mage.cards.a;

import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlchemistsGift extends CardImpl {

    public AlchemistsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +1/+1 and gains your choice of deathtouch or lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setText("Target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Target, "", true,
                DeathtouchAbility.getInstance(), LifelinkAbility.getInstance()).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AlchemistsGift(final AlchemistsGift card) {
        super(card);
    }

    @Override
    public AlchemistsGift copy() {
        return new AlchemistsGift(this);
    }
}
