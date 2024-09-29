package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ChangeMaxNumberThatCanAttackSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudoonEnforcers extends CardImpl {

    public JudoonEnforcers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // No more than one creature can attack you each combat.
        this.addAbility(new SimpleStaticAbility(new ChangeMaxNumberThatCanAttackSourceEffect(1)));

        // Suspend 6--{1}{R}{W}
        this.addAbility(new SuspendAbility(6, new ManaCostsImpl<>("{1}{R}{W}"), this));
    }

    private JudoonEnforcers(final JudoonEnforcers card) {
        super(card);
    }

    @Override
    public JudoonEnforcers copy() {
        return new JudoonEnforcers(this);
    }
}
