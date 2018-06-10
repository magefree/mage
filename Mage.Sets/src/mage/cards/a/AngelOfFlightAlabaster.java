package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

public final class AngelOfFlightAlabaster extends CardImpl {
    private static final FilterCard filter = new FilterCard("Spirit card from your graveyard");

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public AngelOfFlightAlabaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect(), TargetController.YOU, false);
        Target target = new TargetCardInYourGraveyard(filter);
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
