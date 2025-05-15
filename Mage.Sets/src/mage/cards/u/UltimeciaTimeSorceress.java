package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author balazskristof
 */
public final class UltimeciaTimeSorceress extends CardImpl {

    public UltimeciaTimeSorceress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.secondSideCardClazz = mage.cards.u.UltimeciaOmnipotent.class;

        // Whenever Ultimecia enters or attacks, surveil 2.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SurveilEffect(2)));

        // At the beginning of your end step, you may pay {4}{U}{U}{B}{B} and exile eight cards from your graveyard. If you do, transform Ultimecia.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect(),
                new CompositeCost(
                        new ManaCostsImpl<>("{4}{U}{U}{B}{B}"),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(8)),
                        "{4}{U}{U}{B}{B} and exile eight cards from your graveyard"
                )
        )));
        this.addAbility(new TransformAbility());
    }

    private UltimeciaTimeSorceress(final UltimeciaTimeSorceress card) {
        super(card);
    }

    @Override
    public UltimeciaTimeSorceress copy() {
        return new UltimeciaTimeSorceress(this);
    }
}
