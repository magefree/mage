package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarettiRocketeerEngineer extends CardImpl {

    public DarettiRocketeerEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Daretti's power is equal to the greatest mana value among artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS)
                        .setText("{this}'s power is equal to the greatest mana value among artifacts you control")
        ));

        // Whenever Daretti enters or attacks, choose target artifact card in your graveyard. You may sacrifice an artifact. If you do, return the chosen card to the battlefield.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT)
        ).setText("choose target artifact card in your graveyard. You may sacrifice an artifact. If you do, return the chosen card to the battlefield"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private DarettiRocketeerEngineer(final DarettiRocketeerEngineer card) {
        super(card);
    }

    @Override
    public DarettiRocketeerEngineer copy() {
        return new DarettiRocketeerEngineer(this);
    }
}