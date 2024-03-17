package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SynthInfiltrator extends CardImpl {

    public SynthInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{U}");
        
        this.subtype.add(SubType.SYNTH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // You may have Synth Infiltrator enters the battlefield as a copy of any creature on the battlefield, except it's a Synth artifact creatire in addition to its other types.
    }

    private SynthInfiltrator(final SynthInfiltrator card) {
        super(card);
    }

    @Override
    public SynthInfiltrator copy() {
        return new SynthInfiltrator(this);
    }
}
