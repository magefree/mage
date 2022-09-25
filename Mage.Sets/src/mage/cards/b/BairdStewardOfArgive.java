
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 * @author JRHerlehy Created on 4/4/18.
 */
public final class BairdStewardOfArgive extends CardImpl {

    public BairdStewardOfArgive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Creatures can't attack you or a planeswalker you control unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouUnlessPayAllEffect(new ManaCostsImpl<>("{1}"), true)
                .setText("Creatures can't attack you or planeswalkers you control unless their controller pays {1} for each of those creatures")));
    }

    private BairdStewardOfArgive(final BairdStewardOfArgive card) {
        super(card);
    }

    @Override
    public BairdStewardOfArgive copy() {
        return new BairdStewardOfArgive(this);
    }
}
