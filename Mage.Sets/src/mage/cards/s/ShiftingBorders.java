package mage.cards.s;

import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ShiftingBorders extends CardImpl {

    public ShiftingBorders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");
        this.subtype.add(SubType.ARCANE);

        // Exchange control of two target lands.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target lands"));
        this.getSpellAbility().addTarget(new TargetLandPermanent(2).withChooseHint("exchange control"));

        // Splice onto Arcane {3}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{3}{U}"));
    }

    private ShiftingBorders(final ShiftingBorders card) {
        super(card);
    }

    @Override
    public ShiftingBorders copy() {
        return new ShiftingBorders(this);
    }
}
