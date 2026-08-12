package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.VillainToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class HYDRATroopers extends CardImpl {

    public HYDRATroopers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, create a tapped 2/1 black Villain creature token with menace if there are two or more creature cards in your graveyard. Otherwise, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new VillainToken(), 1, true),
                new MillCardsControllerEffect(2),
                new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_CREATURES),
                "create a tapped 2/1 black Villain creature token with menace if there are two or more creature cards in your graveyard. Otherwise, mill two cards"
        )));
    }

    private HYDRATroopers(final HYDRATroopers card) {
        super(card);
    }

    @Override
    public HYDRATroopers copy() {
        return new HYDRATroopers(this);
    }
}
