package mage.cards.b;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaseCamp extends CardImpl {

    public BaseCamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Base Camp enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Cleric, Rogue, Warrior, or Wizard spell or to activate an ability of a Cleric, Rogue, Warrior, or Wizard.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 1, new BaseCampManaBuilder()
        ));
    }

    private BaseCamp(final BaseCamp card) {
        super(card);
    }

    @Override
    public BaseCamp copy() {
        return new BaseCamp(this);
    }
}

class BaseCampManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new BaseCampConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Cleric, Rogue, Warrior, or Wizard spell " +
                "or to activate an ability of a Cleric, Rogue, Warrior, or Wizard";
    }
}

class BaseCampConditionalMana extends ConditionalMana {

    public BaseCampConditionalMana(Mana mana) {
        super(mana);
        addCondition(BaseCampCondition.instance);
    }
}

enum BaseCampCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && (
                object.hasSubtype(SubType.CLERIC, game)
                        || object.hasSubtype(SubType.ROGUE, game)
                        || object.hasSubtype(SubType.WARRIOR, game)
                        || object.hasSubtype(SubType.WIZARD, game)
        );
    }
}
