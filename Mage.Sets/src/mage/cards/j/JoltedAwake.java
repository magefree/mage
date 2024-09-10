package mage.cards.j;

import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JoltedAwake extends CardImpl {

    public JoltedAwake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Choose up to one target artifact or creature card in your graveyard. You get {E}{E}. Then you may pay an amount of {E} equal to that card's mana value. If you do, return it from your graveyard to the battlefield.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new InfoEffect("Choose up to one target artifact or creature card in your graveyard."));
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return it from your graveyard to the battlefield"),
                new PayEnergyCost(TargetManaValue.instance, "pay an amount of {E} equal to that card's mana value"),
                "pay an amount of {E} equal to that card's mana value?",
                true
        ).concatBy("Then"));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private JoltedAwake(final JoltedAwake card) {
        super(card);
    }

    @Override
    public JoltedAwake copy() {
        return new JoltedAwake(this);
    }
}
