
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BondedConstruct extends CardImpl {

    public BondedConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bonded Construct can't attack alone.
        this.addAbility(new SimpleStaticAbility(new CantAttackAloneSourceEffect()));
    }

    private BondedConstruct(final BondedConstruct card) {
        super(card);
    }

    @Override
    public BondedConstruct copy() {
        return new BondedConstruct(this);
    }
}
