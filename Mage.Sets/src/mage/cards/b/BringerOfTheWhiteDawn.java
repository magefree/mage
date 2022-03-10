package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class BringerOfTheWhiteDawn extends CardImpl {

    public BringerOfTheWhiteDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{W}{W}");
        this.subtype.add(SubType.BRINGER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may pay {W}{U}{B}{R}{G} rather than pay Bringer of the White Dawn's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{W}{U}{B}{R}{G}")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, you may return target artifact card from your graveyard to the battlefield.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private BringerOfTheWhiteDawn(final BringerOfTheWhiteDawn card) {
        super(card);
    }

    @Override
    public BringerOfTheWhiteDawn copy() {
        return new BringerOfTheWhiteDawn(this);
    }
}
