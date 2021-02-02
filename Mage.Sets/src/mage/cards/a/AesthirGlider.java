
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Sir-Speshkitty
 */
public final class AesthirGlider extends CardImpl {

    public AesthirGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Aesthir Glider can't block.
        this.addAbility(new CantBlockAbility());
    }

    private AesthirGlider(final AesthirGlider card) {
        super(card);
    }

    @Override
    public AesthirGlider copy() {
        return new AesthirGlider(this);
    }
}
