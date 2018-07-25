package mage.cards.v;

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
public final class Vulture extends CardImpl {

    public Vulture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Vulture is turned face up, destroy target creature blocked by Vulture.
        // Morph {4}{W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{W}")));

    }

    public Vulture(final Vulture card) {
        super(card);
    }

    @Override
    public Vulture copy() {
        return new Vulture(this);
    }
}
