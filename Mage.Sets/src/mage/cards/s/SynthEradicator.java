package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SynthEradicator extends CardImpl {

    public SynthEradicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Synth Eradicator attacks, exile the top card of your library. You may get {E}{E}. If you don't, you may play that card this turn.
        // {T}, Pay {E}{E}{E}: Synth Eradicator deals 3 damage to any target.
    }

    private SynthEradicator(final SynthEradicator card) {
        super(card);
    }

    @Override
    public SynthEradicator copy() {
        return new SynthEradicator(this);
    }
}
