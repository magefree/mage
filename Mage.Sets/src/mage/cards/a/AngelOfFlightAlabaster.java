package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public final class AngelOfFlightAlabaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit card from your graveyard");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public AngelOfFlightAlabaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private AngelOfFlightAlabaster(final AngelOfFlightAlabaster card) {
        super(card);
    }

    @Override
    public AngelOfFlightAlabaster copy() {
        return new AngelOfFlightAlabaster(this);
    }
}
