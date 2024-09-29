package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayerHandCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author cbrianhill
 */
public final class DreambornMuse extends CardImpl {

    public DreambornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's upkeep, that player puts the top X cards of their library into their graveyard, where X is the number of cards in their hand.
        MillCardsTargetEffect effect = new MillCardsTargetEffect(CardsInTargetPlayerHandCount.instance);
        effect.setText("that player mills X cards, where X is the number of cards in their hand");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.ANY, false));
    }

    private DreambornMuse(final DreambornMuse card) {
        super(card);
    }

    @Override
    public DreambornMuse copy() {
        return new DreambornMuse(this);
    }
}
