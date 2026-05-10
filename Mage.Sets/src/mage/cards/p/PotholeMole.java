package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PotholeMole extends CardImpl {

    public PotholeMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MOLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, mill three cards, then you may return a land card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(true, StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD,
                PutCards.HAND).concatBy(", then"));
        this.addAbility(ability);
    }

    private PotholeMole(final PotholeMole card) {
        super(card);
    }

    @Override
    public PotholeMole copy() {
        return new PotholeMole(this);
    }
}
