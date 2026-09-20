package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PrimalWitchstalker extends CardImpl {

    public PrimalWitchstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, mill four cards. When you do, return target land card from your graveyard to the battlefield tapped.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ReturnFromGraveyardToBattlefieldTargetEffect(true), false
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD));
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoWhenCostPaid(reflexive, new MillCardsCost(4), "", false)
        ));
    }

    private PrimalWitchstalker(final PrimalWitchstalker card) {
        super(card);
    }

    @Override
    public PrimalWitchstalker copy() {
        return new PrimalWitchstalker(this);
    }
}
