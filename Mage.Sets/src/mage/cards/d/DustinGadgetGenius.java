package mage.cards.d;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DustinGadgetGenius extends CardImpl {

    public DustinGadgetGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add {C}{C}. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 2, new DustinGadgetGeniusManaBuilder()
        ));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private DustinGadgetGenius(final DustinGadgetGenius card) {
        super(card);
    }

    @Override
    public DustinGadgetGenius copy() {
        return new DustinGadgetGenius(this);
    }
}

class DustinGadgetGeniusManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new DustinGadgetGeniusConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class DustinGadgetGeniusConditionalMana extends ConditionalMana {

    DustinGadgetGeniusConditionalMana(Mana mana) {
        super(mana);
        addCondition(DustinGadgetGeniusCondition.instance);
    }
}

enum DustinGadgetGeniusCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null && object.isArtifact(game);
    }
}
