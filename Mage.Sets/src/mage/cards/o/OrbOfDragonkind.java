package mage.cards.o;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class OrbOfDragonkind extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public OrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // {1}, {T}: Add two mana in any combination of colors. Spend this mana only to cast Dragon spells or to activate abilities of Dragons.
        Ability ability = new ConditionalAnyColorManaAbility(new GenericManaCost(1), 2, new OrbOfDragonkindManaBuilder());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {R}, {T}, Sacrifice Orb of Dragonkind: Look at the top seven cards of your library.
        // You may reveal a Dragon card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(7, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OrbOfDragonkind(final OrbOfDragonkind card) {
        super(card);
    }

    @Override
    public OrbOfDragonkind copy() {
        return new OrbOfDragonkind(this);
    }
}

class OrbOfDragonkindManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OrbOfDragonkindConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
    }
}

class OrbOfDragonkindConditionalMana extends ConditionalMana {

    public OrbOfDragonkindConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
        addCondition(OrbOfDragonkindManaCondition.instance);
    }
}

enum OrbOfDragonkindManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.DRAGON, game);
    }
}
