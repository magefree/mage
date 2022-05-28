
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterMana;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class AtalyaSamiteMaster extends CardImpl {

    private static final FilterMana filterWhiteMana = new FilterMana();

    static {
        filterWhiteMana.setWhite(true);
    }

    public AtalyaSamiteMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {X}, {tap}: Choose one - Prevent the next X damage that would be dealt to target creature this turn; or you gain X life. Spend only white mana on X. 
        PreventDamageToTargetEffect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, false, true, ManacostVariableValue.REGULAR);
        effect.setText("Prevent the next X damage that would be dealt to target creature this turn. Spend only white mana on X.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());

        VariableCost variableCost = ability.getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterWhiteMana);
        }

        ability.addTarget(new TargetCreaturePermanent());

        // or you gain X life
        Mode mode = new Mode(new GainLifeEffect(ManacostVariableValue.REGULAR).setText("You gain X life. Spend only white mana on X."));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private AtalyaSamiteMaster(final AtalyaSamiteMaster card) {
        super(card);
    }

    @Override
    public AtalyaSamiteMaster copy() {
        return new AtalyaSamiteMaster(this);
    }
}
