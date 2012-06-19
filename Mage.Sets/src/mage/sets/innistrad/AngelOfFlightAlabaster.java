package mage.sets.innistrad;

import mage.Constants;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public class AngelOfFlightAlabaster extends CardImpl<AngelOfFlightAlabaster> {
    private static final FilterCard filter = new FilterCard("Spirit card from your graveyard");

    static {
        filter.getSubtype().add("Spirit");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public AngelOfFlightAlabaster(UUID ownerId) {
        super(ownerId, 2, "Angel of Flight Alabaster", Constants.Rarity.RARE, new Constants.CardType[]{Constants.CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect(), Constants.TargetController.YOU, false);
        Target target = new TargetCardInYourGraveyard(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);


    }

    public AngelOfFlightAlabaster(final AngelOfFlightAlabaster card) {
        super(card);
    }

    @Override
    public AngelOfFlightAlabaster copy() {
        return new AngelOfFlightAlabaster(this);
    }
}
