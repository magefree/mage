package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarrionCruiser extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or Vehicle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public CarrionCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this Vehicle enters, mill two cards. Then return a creature or Vehicle card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false, filter, PutCards.HAND)
                .concatBy("Then"));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private CarrionCruiser(final CarrionCruiser card) {
        super(card);
    }

    @Override
    public CarrionCruiser copy() {
        return new CarrionCruiser(this);
    }
}
