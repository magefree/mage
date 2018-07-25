package mage.cards.g;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author NinthWorld
 */
public final class GhostAcademy extends CardImpl {

    public GhostAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Ghost Academy enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new TapSourceCost()));

        // {T}: Add {C}{C} to your mana pool. Spend this mana only to cast creatures face down.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new GhostAcademyManaBuilder()));
    }

    public GhostAcademy(final GhostAcademy card) {
        super(card);
    }

    @Override
    public GhostAcademy copy() {
        return new GhostAcademy(this);
    }
}

class GhostAcademyManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GhostAcademyConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creatures face down";
    }
}

class GhostAcademyConditionalMana extends ConditionalMana {

    public GhostAcademyConditionalMana(Mana mana) {
        super(mana);
        addCondition(new GhostAcademyManaCondition());
    }
}

class GhostAcademyManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object instanceof Spell) {
            if (((Spell) object).isFaceDown(game)) {
                return true;
            }
        }
        return false;
    }
}
