package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeweledLotus extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("your commander");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public JeweledLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}, Sacrifice Jeweled Lotus: Add three mana of any one color. Spend this mana only to cast your commander.
        Ability ability = new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 3, new ConditionalSpellManaBuilder(filter), true
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private JeweledLotus(final JeweledLotus card) {
        super(card);
    }

    @Override
    public JeweledLotus copy() {
        return new JeweledLotus(this);
    }
}
