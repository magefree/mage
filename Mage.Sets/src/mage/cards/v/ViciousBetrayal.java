package mage.cards.v;

import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ViciousBetrayal extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public ViciousBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");


        // As an additional cost to cast Vicious Betrayal, sacrifice any number of creatures.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledCreaturePermanent(), true));
        // Target creature gets +2/+2 until end of turn for each creature sacrificed this way.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                .setText("target creature gets +2/+2 until end of turn for each creature sacrificed this way"));
    }

    private ViciousBetrayal(final ViciousBetrayal card) {
        super(card);
    }

    @Override
    public ViciousBetrayal copy() {
        return new ViciousBetrayal(this);
    }
}
