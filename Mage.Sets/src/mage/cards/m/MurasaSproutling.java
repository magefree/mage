package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurasaSproutling extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with a kicker ability from your graveyard");

    static {
        filter.add(new AbilityPredicate(KickerAbility.class));
    }

    public MurasaSproutling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Murasa Sproutling enters the battlefield, if it was kicked, return target card with a kicker ability from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()),
                KickedCondition.instance, "When {this} enters the battlefield, if it was kicked, " +
                "return target card with a kicker ability from your graveyard to your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MurasaSproutling(final MurasaSproutling card) {
        super(card);
    }

    @Override
    public MurasaSproutling copy() {
        return new MurasaSproutling(this);
    }
}
