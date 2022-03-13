package mage.cards.p;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.ModularAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerDepot extends CardImpl {

    public PowerDepot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Power Depot enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new PowerDepotManaBuilder()));

        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private PowerDepot(final PowerDepot card) {
        super(card);
    }

    @Override
    public PowerDepot copy() {
        return new PowerDepot(this);
    }
}

class PowerDepotManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new PowerDepotConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class PowerDepotConditionalMana extends ConditionalMana {

    PowerDepotConditionalMana(Mana mana) {
        super(mana);
        this.addCondition(PowerDepotCondition.instance);
    }
}

enum PowerDepotCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}
