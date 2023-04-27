
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class FromUnderTheFloorboards extends CardImpl {

    public FromUnderTheFloorboards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Madness {X}{B}{B} <i>(If you discard this card discard it into exile. When you do cast it for its madness cost or put it into your graveyard.
        Ability ability = (new MadnessAbility(new ManaCostsImpl<>("{X}{B}{B}")));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // Create three 2/2 black Zombie creature tokens tapped and you gain 3 life.        
        // If From Under the Floorboards's madness cost was paid, instead create X of those tokens tapped and you gain X life.      
        DynamicValue xValue = new FromUnderTheFloorboardsManacostVariableValue();
        Effect effect = new CreateTokenEffect(new ZombieToken(), xValue, true, false);
        effect.setText("Create three 2/2 black Zombie creature tokens tapped and you gain 3 life. If {this} madness cost was paid, instead create X of those tokens tapped and you gain X life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue));        
    }

    private FromUnderTheFloorboards(final FromUnderTheFloorboards card) {
        super(card);
    }

    @Override
    public FromUnderTheFloorboards copy() {
        return new FromUnderTheFloorboards(this);
    }
}

class FromUnderTheFloorboardsManacostVariableValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ManaCosts manaCosts = sourceAbility.getManaCostsToPay();
        if (manaCosts.getVariableCosts().isEmpty()) {
            return 3;
        }
        return sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public FromUnderTheFloorboardsManacostVariableValue copy() {
        return new FromUnderTheFloorboardsManacostVariableValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
