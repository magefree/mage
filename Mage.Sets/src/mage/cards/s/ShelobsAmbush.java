package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShelobsAmbush extends CardImpl {

    public ShelobsAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +1/+2 and gains deathtouch until end of turn. Create a Food token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2).setText("target creature gets +1/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()).setText("and gains deathtouch until end of turn"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShelobsAmbush(final ShelobsAmbush card) {
        super(card);
    }

    @Override
    public ShelobsAmbush copy() {
        return new ShelobsAmbush(this);
    }
}
