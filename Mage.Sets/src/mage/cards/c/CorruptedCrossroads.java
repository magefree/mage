
package mage.cards.c;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author fireshoes
 */
public final class CorruptedCrossroads extends CardImpl {

    public CorruptedCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {T}, Pay 1 life: Add one mana of any color. Spend this mana only to cast a spell with devoid.
        Ability ability = new ConditionalAnyColorManaAbility(1, new BlightedCrossroadsManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private CorruptedCrossroads(final CorruptedCrossroads card) {
        super(card);
    }

    @Override
    public CorruptedCrossroads copy() {
        return new CorruptedCrossroads(this);
    }
}

class BlightedCrossroadsManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new BlightedCrossroadsConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell with devoid";
    }
}

class BlightedCrossroadsConditionalMana extends ConditionalMana {

    public BlightedCrossroadsConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a spell with devoid";
        addCondition(new BlightedCrossroadsManaCondition());
    }
}

class BlightedCrossroadsManaCondition implements Condition {
    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null) {
            for (Ability ability: object.getAbilities()) {
                if (ability instanceof DevoidAbility) {
                    return true;
                }
            }
        }
        return false;
    }
}
