
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterMana;

/**
 *
 * @author LevelX2
 */
public final class CryptRats extends CardImpl {

    static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }

    public CryptRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}: Crypt Rats deals X damage to each creature and each player. Spend only black mana on X.
        Effect effect = new DamageEverythingEffect(ManacostVariableValue.REGULAR);
        effect.setText("{this} deals X damage to each creature and each player. Spend only black mana on X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect,new ManaCostsImpl<>("{X}"));
        VariableCost variableCost = ability.getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlack);
        }
        this.addAbility(ability);
    }

    private CryptRats(final CryptRats card) {
        super(card);
    }

    @Override
    public CryptRats copy() {
        return new CryptRats(this);
    }
}
