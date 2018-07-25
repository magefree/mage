package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SiegeTank extends CardImpl {

    public SiegeTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Siege Tank doesn't untap during your untap step.
        // Morph {R}{W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{R}{W}")));

        // At the beginning of your end step, you may turn Siege Tank face down.
    }

    public SiegeTank(final SiegeTank card) {
        super(card);
    }

    @Override
    public SiegeTank copy() {
        return new SiegeTank(this);
    }
}
