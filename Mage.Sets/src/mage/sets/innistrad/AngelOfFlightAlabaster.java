package mage.sets.innistrad;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public class AngelOfFlightAlabaster extends CardImpl<AngelOfFlightAlabaster> {
    private static final FilterCard filter = new FilterCard("Spirit card from your graveyard");

    static {
        filter.add(new SubtypePredicate("Spirit"));
    }

    public AngelOfFlightAlabaster(UUID ownerId) {
        super(ownerId, 2, "Angel of Flight Alabaster", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect(), TargetController.YOU, false);
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
