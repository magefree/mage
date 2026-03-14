package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphAndMikeyTroublemakers extends CardImpl {

    public RaphAndMikeyTroublemakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R/G}{R/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Raph & Mikey attack, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield tapped and attacking and the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_CREATURE, PutCards.BATTLEFIELD_TAPPED_ATTACKING, PutCards.BOTTOM_RANDOM
        )).setTriggerPhrase("Whenever {this} attack, "));
    }

    private RaphAndMikeyTroublemakers(final RaphAndMikeyTroublemakers card) {
        super(card);
    }

    @Override
    public RaphAndMikeyTroublemakers copy() {
        return new RaphAndMikeyTroublemakers(this);
    }
}
