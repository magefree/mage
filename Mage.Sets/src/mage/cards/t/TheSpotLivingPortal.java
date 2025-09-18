package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PutSourceOnBottomOwnerLibraryCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Jmlundeen
 */
public final class TheSpotLivingPortal extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland permanent card from a graveyard");

    static {
        filter.add(Predicates.or(
                Arrays.stream(CardType.values())
                        .filter(CardType::isPermanentType)
                        .filter(type -> type != CardType.LAND)
                        .map(CardType::getPredicate)
                        .collect(Collectors.toSet()))
        );
    }

    public TheSpotLivingPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When The Spot enters, exile up to one target nonland permanent and up to one target nonland permanent card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect()
                .setTargetPointer(new EachTargetPointer()));
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        this.addAbility(ability);

        // When The Spot dies, put him on the bottom of his owner's library. If you do, return the exiled cards to their owners' hands.
        DoIfCostPaid effect = new DoIfCostPaid(
                new ReturnFromExileForSourceEffect(Zone.HAND)
                        .withText(true, true, false),
                null,
                new PutSourceOnBottomOwnerLibraryCost()
                        .setText("put him on the bottom of his owner's library"),
                false
        );
        this.addAbility(new DiesSourceTriggeredAbility(effect, false));
    }

    private TheSpotLivingPortal(final TheSpotLivingPortal card) {
        super(card);
    }

    @Override
    public TheSpotLivingPortal copy() {
        return new TheSpotLivingPortal(this);
    }
}
