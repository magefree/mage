package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenuredOilcaster extends CardImpl {

    public TenuredOilcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Tenured Oilcaster gets +3/+0 as long as an opponent has eight or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT, "{this} gets +3/+0 as long as " +
                "an opponent has eight or more cards in their graveyard"
        )).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // Whenever Tenured Oilcaster attacks or blocks, each player mills a card.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new MillCardsEachPlayerEffect(1, TargetController.EACH_PLAYER), false
        ));
    }

    private TenuredOilcaster(final TenuredOilcaster card) {
        super(card);
    }

    @Override
    public TenuredOilcaster copy() {
        return new TenuredOilcaster(this);
    }
}
