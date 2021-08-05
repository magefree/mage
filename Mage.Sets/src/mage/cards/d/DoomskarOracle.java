package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomskarOracle extends CardImpl {

    public DoomskarOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, you gain 2 life.
        this.addAbility(new CastSecondSpellTriggeredAbility(new GainLifeEffect(2)));

        // Foretell {W}
        this.addAbility(new ForetellAbility(this, "{W}"));
    }

    private DoomskarOracle(final DoomskarOracle card) {
        super(card);
    }

    @Override
    public DoomskarOracle copy() {
        return new DoomskarOracle(this);
    }
}
