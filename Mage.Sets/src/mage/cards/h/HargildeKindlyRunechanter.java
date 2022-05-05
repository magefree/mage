package mage.cards.h;

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
public final class HargildeKindlyRunechanter extends CardImpl {

    public HargildeKindlyRunechanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add {C}{C}. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 2, new HargildeKindlyRunechanterManaBuilder()
        ));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private HargildeKindlyRunechanter(final HargildeKindlyRunechanter card) {
        super(card);
    }

    @Override
    public HargildeKindlyRunechanter copy() {
        return new HargildeKindlyRunechanter(this);
    }
}

class HargildeKindlyRunechanterManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new HargildeKindlyRunechanterConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class HargildeKindlyRunechanterConditionalMana extends ConditionalMana {

    HargildeKindlyRunechanterConditionalMana(Mana mana) {
        super(mana);
        addCondition(HargildeKindlyRunechanterCondition.instance);
    }
}

enum HargildeKindlyRunechanterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}
