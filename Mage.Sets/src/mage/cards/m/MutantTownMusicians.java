package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MutantTownMusicians extends CardImpl {

    public MutantTownMusicians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Alliance -- Whenever another creature you control enters, this creature gets +1/+0 until end of turn.
        this.addAbility(new AllianceAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private MutantTownMusicians(final MutantTownMusicians card) {
        super(card);
    }

    @Override
    public MutantTownMusicians copy() {
        return new MutantTownMusicians(this);
    }
}
