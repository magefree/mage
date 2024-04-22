package mage.cards.i;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IxallisLorekeeper extends CardImpl {

    public IxallisLorekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color. Spend this mana only to cast a Dinosaur spell or activate an ability of a Dinosaur source.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 1, new IxallisLorekeeperManaBuilder()
        ));
    }

    private IxallisLorekeeper(final IxallisLorekeeper card) {
        super(card);
    }

    @Override
    public IxallisLorekeeper copy() {
        return new IxallisLorekeeper(this);
    }
}

class IxallisLorekeeperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new IxallisLorekeeperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Dinosaur spell or activate an ability of a Dinosaur source";
    }
}

class IxallisLorekeeperConditionalMana extends ConditionalMana {

    public IxallisLorekeeperConditionalMana(Mana mana) {
        super(mana);
        addCondition(IxallisLorekeeperCondition.instance);
    }
}

enum IxallisLorekeeperCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.DINOSAUR, game);
    }
}
