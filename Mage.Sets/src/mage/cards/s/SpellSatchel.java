package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellSatchel extends CardImpl {

    public SpellSatchel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, put a book counter on Spell Satchel.
        this.addAbility(new MagecraftAbility(new AddCountersSourceEffect(CounterType.BOOK.createInstance())));

        // {T}, Remove a book counter from Spell Satchel: Add {C}.
        Ability ability = new ColorlessManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.BOOK.createInstance()));
        this.addAbility(ability);

        // {3}, {T}, Remove three book counters from Spell Satchel: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.BOOK.createInstance(3)));
        this.addAbility(ability);
    }

    private SpellSatchel(final SpellSatchel card) {
        super(card);
    }

    @Override
    public SpellSatchel copy() {
        return new SpellSatchel(this);
    }
}
