package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritOfMalevolence extends CardImpl {

    public SpiritOfMalevolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Spirit of Malevolence dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SpiritOfMalevolence(final SpiritOfMalevolence card) {
        super(card);
    }

    @Override
    public SpiritOfMalevolence copy() {
        return new SpiritOfMalevolence(this);
    }
}
