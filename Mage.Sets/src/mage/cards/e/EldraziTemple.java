
package mage.cards.e;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

/**
 *
 * @author Loki, nantuko
 */
public final class EldraziTemple extends CardImpl {

    public EldraziTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.  
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new EldraziTempleManaBuilder()));
    }

    private EldraziTemple(final EldraziTemple card) {
        super(card);
    }

    @Override
    public EldraziTemple copy() {
        return new EldraziTemple(this);
    }
}

class EldraziTempleManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new EldraziTempleConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi";
    }
}

class EldraziTempleConditionalMana extends ConditionalMana {

    public EldraziTempleConditionalMana(Mana mana) {
        super(mana);
        addCondition(new EldraziTempleCondition());
    }
}

class EldraziTempleCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.ELDRAZI, game) && object.getColor(game).isColorless();
    }
}
