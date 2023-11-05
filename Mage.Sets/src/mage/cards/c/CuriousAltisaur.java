package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousAltisaur extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR, "a Dinosaur you control");

    public CuriousAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever a Dinosaur you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                filter, false, SetTargetPointer.NONE, true
        ));
    }

    private CuriousAltisaur(final CuriousAltisaur card) {
        super(card);
    }

    @Override
    public CuriousAltisaur copy() {
        return new CuriousAltisaur(this);
    }
}
