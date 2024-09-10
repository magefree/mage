package mage.cards.i;

import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IntrepidStablemaster extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Mount or Vehicle spells");

    static {
        filter.add(Predicates.or(SubType.MOUNT.getPredicate(), SubType.VEHICLE.getPredicate()));
    }

    public IntrepidStablemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add two mana of any one color. Spend this mana only to cast Mount or Vehicle spells.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2,
                new ConditionalSpellManaBuilder(filter), true
        ));
    }

    private IntrepidStablemaster(final IntrepidStablemaster card) {
        super(card);
    }

    @Override
    public IntrepidStablemaster copy() {
        return new IntrepidStablemaster(this);
    }
}
