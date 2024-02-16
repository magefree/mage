package mage.cards.i;

import mage.ConditionalMana;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngaAndEsika extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a creature spell, if three or more mana from creatures was spent to cast it");

    static {
        filter.add(IngaAndEsikaPredicate.instance);
    }

    public IngaAndEsika(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new ConditionalAnyColorManaAbility(1, new IngaAndEsikaManaBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and \"{T}: Add one mana of any color. Spend this mana only to cast a creature spell.\""));
        this.addAbility(ability);

        // Whenever you cast a creature spell, if three or more mana from creatures was spent to cast it, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private IngaAndEsika(final IngaAndEsika card) {
        super(card);
    }

    @Override
    public IngaAndEsika copy() {
        return new IngaAndEsika(this);
    }
}

class IngaAndEsikaManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}

enum IngaAndEsikaPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return ManaPaidSourceWatcher.getCreaturePaid(input.getId(), game) >= 3;
    }
}
