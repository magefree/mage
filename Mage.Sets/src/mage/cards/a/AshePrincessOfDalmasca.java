package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
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
public final class AshePrincessOfDalmasca extends CardImpl {

    public AshePrincessOfDalmasca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Ashe attacks, look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_ARTIFACT_AN,
                PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private AshePrincessOfDalmasca(final AshePrincessOfDalmasca card) {
        super(card);
    }

    @Override
    public AshePrincessOfDalmasca copy() {
        return new AshePrincessOfDalmasca(this);
    }
}
