package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SimicCharm extends CardImpl {

    public SimicCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        //Choose one - Target creature gets +3/+3 until end of turn
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        //permanents you control gain hexproof until end of turn
        this.getSpellAbility().addMode(new Mode(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS)));

        //return target creature to its owner's hand.
        Mode mode2 = new Mode(new ReturnToHandTargetEffect());
        mode2.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
    }

    private SimicCharm(final SimicCharm card) {
        super(card);
    }

    @Override
    public SimicCharm copy() {
        return new SimicCharm(this);
    }
}
