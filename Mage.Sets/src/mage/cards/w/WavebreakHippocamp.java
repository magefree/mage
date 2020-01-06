package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WavebreakHippocamp extends CardImpl {

    public WavebreakHippocamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your first spell during each opponent's turn, draw a card.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private WavebreakHippocamp(final WavebreakHippocamp card) {
        super(card);
    }

    @Override
    public WavebreakHippocamp copy() {
        return new WavebreakHippocamp(this);
    }
}
