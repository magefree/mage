package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackbloomRogue extends CardImpl {

    public BlackbloomRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.b.BlackbloomBog.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // Blackbloom Rogue gets +3/+0 as long as an opponent has eight or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT, "{this} gets +3/+0 as long as " +
                "an opponent has eight or more cards in their graveyard"
        )).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private BlackbloomRogue(final BlackbloomRogue card) {
        super(card);
    }

    @Override
    public BlackbloomRogue copy() {
        return new BlackbloomRogue(this);
    }
}
