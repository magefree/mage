package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AspirantsAscent extends CardImpl {

    public AspirantsAscent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Until end of turn, target creature gets +1/+3 and gains flying and toxic 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3)
                .setText("until end of turn, target creature gets +1/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new ToxicAbility(1))
                .setText("and toxic 1"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AspirantsAscent(final AspirantsAscent card) {
        super(card);
    }

    @Override
    public AspirantsAscent copy() {
        return new AspirantsAscent(this);
    }
}
