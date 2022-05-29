
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AvenInitiate extends CardImpl {

    public AvenInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Embalm {6}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{6}{U}"), this));

    }

    private AvenInitiate(final AvenInitiate card) {
        super(card);
    }

    @Override
    public AvenInitiate copy() {
        return new AvenInitiate(this);
    }
}
