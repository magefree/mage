package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Wraith extends CardImpl {

    public Wraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Morph {W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{W}")));

        // When Wraith is turned face up, you may return it to its owner's hand.
    }

    public Wraith(final Wraith card) {
        super(card);
    }

    @Override
    public Wraith copy() {
        return new Wraith(this);
    }
}
