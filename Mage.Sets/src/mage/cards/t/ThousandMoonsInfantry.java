package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ThousandMoonsInfantry extends CardImpl {

    public ThousandMoonsInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Untap Thousand Moons Infantry during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(new UntapSourceDuringEachOtherPlayersUntapStepEffect()));

    }

    private ThousandMoonsInfantry(final ThousandMoonsInfantry card) {
        super(card);
    }

    @Override
    public ThousandMoonsInfantry copy() {
        return new ThousandMoonsInfantry(this);
    }
}
