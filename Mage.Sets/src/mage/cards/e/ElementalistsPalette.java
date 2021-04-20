package mage.cards.e;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalistsPalette extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public ElementalistsPalette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you cast a spell with {X} in its mana cost, put two charge counters on Elementalist's Palette.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)), filter, false
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add {C} for each charge counter on Elementalist's Palette. Spend this mana only on costs that contain {X}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new ElementalistsPaletteManaEffect(), new TapSourceCost()
        ));
    }

    private ElementalistsPalette(final ElementalistsPalette card) {
        super(card);
    }

    @Override
    public ElementalistsPalette copy() {
        return new ElementalistsPalette(this);
    }
}

class ElementalistsPaletteManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new ElementalistsPaletteManaBuilder();

    ElementalistsPaletteManaEffect() {
        this.staticText = "add {C} for each charge counter on {this}. Spend this mana only on costs that contain {X}";
    }

    private ElementalistsPaletteManaEffect(final ElementalistsPaletteManaEffect effect) {
        super(effect);
    }

    @Override
    public ElementalistsPaletteManaEffect copy() {
        return new ElementalistsPaletteManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            netMana.add(manaBuilder.setMana(Mana.ColorlessMana(
                    permanent.getCounters(game).getCount(CounterType.CHARGE)
            ), source, game).build());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return mana;
        }
        return manaBuilder.setMana(Mana.ColorlessMana(
                permanent.getCounters(game).getCount(CounterType.CHARGE)
        ), source, game).build();
    }
}

class ElementalistsPaletteConditionalMana extends ConditionalMana {

    ElementalistsPaletteConditionalMana(Mana mana) {
        super(mana);
        addCondition(new ElementalistsPaletteManaCondition());
    }
}

class ElementalistsPaletteManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new ElementalistsPaletteConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only on costs that contain {X}";
    }
}

class ElementalistsPaletteManaCondition extends ManaCondition {

    /*
    A “cost that contains {X}” may be a spell’s total cost, an activated ability’s cost, a suspend cost, or a cost you’re
    asked to pay as part of the resolution of a spell or ability (such as Condescend). A spell’s total cost includes either
    its mana cost (printed in the upper right corner) or its alternative cost (such as flashback), as well as any additional
    costs (such as kicker). If it’s something you can spend mana on, it’s a cost. If that cost includes the {X} symbol in it,
    you can spend mana generated by Rosheen on that cost. (2017-11-17)
     */
    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        boolean result;
        if (costToPay instanceof ManaCosts) {
            result = !((ManaCosts) costToPay).getVariableCosts().isEmpty();
        } else {
            result = costToPay instanceof VariableManaCost;
        }
        if (!result) {
            if (game != null && game.inCheckPlayableState()) {
                return true; // TODO: Check the card if there are related abilities with {X} costs.
            }
        }
        return result;
    }
}
