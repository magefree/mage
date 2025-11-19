package mage.cards.h;

import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HermiticHerbalist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Lesson spells");

    static {
        filter.add(SubType.LESSON.getPredicate());
    }

    public HermiticHerbalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add two mana in any combination of colors. Spend this mana only to cast Lesson spells.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 1, new ConditionalSpellManaBuilder(filter), false
        ));
    }

    private HermiticHerbalist(final HermiticHerbalist card) {
        super(card);
    }

    @Override
    public HermiticHerbalist copy() {
        return new HermiticHerbalist(this);
    }
}
