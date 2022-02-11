package mage.cards.t;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TamiyosSafekeeping extends CardImpl {

    public TamiyosSafekeeping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target permanent you control gains hexproof and indestructible until end of turn. You gain 2 life.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("target permanent you control gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private TamiyosSafekeeping(final TamiyosSafekeeping card) {
        super(card);
    }

    @Override
    public TamiyosSafekeeping copy() {
        return new TamiyosSafekeeping(this);
    }
}
