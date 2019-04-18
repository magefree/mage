package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArborealGrazer extends CardImpl {

    public ArborealGrazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Arboreal Grazer enters the battlefield, you may put a land card from your hand onto the battlefield tapped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_LAND_A, false, true
        ), false));

    }

    private ArborealGrazer(final ArborealGrazer card) {
        super(card);
    }

    @Override
    public ArborealGrazer copy() {
        return new ArborealGrazer(this);
    }
}
