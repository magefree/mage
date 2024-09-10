
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.WUBRGInsteadEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author CountAndromalius
 */
public final class JodahArchmageEternal extends CardImpl {

    public JodahArchmageEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may pay WUBRG rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WUBRGInsteadEffect()));
    }

    private JodahArchmageEternal(final JodahArchmageEternal card) {
        super(card);
    }

    @Override
    public JodahArchmageEternal copy() {
        return new JodahArchmageEternal(this);
    }
}
