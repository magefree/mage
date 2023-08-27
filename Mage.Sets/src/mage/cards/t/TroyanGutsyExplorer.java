package mage.cards.t;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TroyanGutsyExplorer extends CardImpl {

    public TroyanGutsyExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {G}{U}. Spend this mana only to cast spells with mana value 5 or greater or spells with X in their mana costs.
        this.addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(),
                new Mana(0, 1, 0, 0, 1, 0, 0, 0),
                new TroyanGutsyExplorerManaBuilder()
        ));

        // {U}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(),
                new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TroyanGutsyExplorer(final TroyanGutsyExplorer card) {
        super(card);
    }

    @Override
    public TroyanGutsyExplorer copy() {
        return new TroyanGutsyExplorer(this);
    }
}

class TroyanGutsyExplorerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new TroyanGutsyExplorerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells with mana value 5 or greater or spells with {X} in their mana costs";
    }
}

class TroyanGutsyExplorerConditionalMana extends ConditionalMana {
    TroyanGutsyExplorerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells with mana value 5 or greater or spells with {X} in their mana costs";
        addCondition(TroyanGutsyExplorerCondition.instance);
    }
}

enum TroyanGutsyExplorerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            // Only for spells.
            return false;
        }

        MageObject object = game.getObject(source);
        return object != null && (object.getManaValue() >= 5 ||
                object.getManaCost().stream().anyMatch(VariableManaCost.class::isInstance));
    }
}
