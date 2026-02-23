package mage.cards.j;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JasmineDragonTeaShop extends CardImpl {

    public JasmineDragonTeaShop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast an Ally spell or activate an ability of an Ally source.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new JasmineDragonTeaShopManaBuilder()));

        // {5}, {T}: Create a 1/1 white Ally creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new AllyToken()), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JasmineDragonTeaShop(final JasmineDragonTeaShop card) {
        super(card);
    }

    @Override
    public JasmineDragonTeaShop copy() {
        return new JasmineDragonTeaShop(this);
    }
}

class JasmineDragonTeaShopManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new JasmineDragonTeaShopConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Ally spell or activate an ability of an Ally source";
    }
}

class JasmineDragonTeaShopConditionalMana extends ConditionalMana {

    JasmineDragonTeaShopConditionalMana(Mana mana) {
        super(mana);
        addCondition(JasmineDragonTeaShopCondition.instance);
    }
}

enum JasmineDragonTeaShopCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.ALLY, game) && !source.isActivated();
    }
}
