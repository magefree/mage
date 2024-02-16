package mage.cards.e;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.XCostManaCondition;
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

class ElementalistsPaletteConditionalMana extends ConditionalMana {

    ElementalistsPaletteConditionalMana(Mana mana) {
        super(mana);
        addCondition(new XCostManaCondition());
    }
}
