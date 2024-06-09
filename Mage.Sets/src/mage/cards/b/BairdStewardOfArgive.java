
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author JRHerlehy Created on 4/4/18.
 */
public final class BairdStewardOfArgive extends CardImpl {

    public BairdStewardOfArgive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Creatures can't attack you or a planeswalker you control unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield,
                new ManaCostsImpl<>("{1}"),
                CantAttackYouUnlessPayAllEffect.Scope.YOU_AND_CONTROLLED_PLANESWALKERS
            )
        ));
    }

    private BairdStewardOfArgive(final BairdStewardOfArgive card) {
        super(card);
    }

    @Override
    public BairdStewardOfArgive copy() {
        return new BairdStewardOfArgive(this);
    }
}
