
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author Styxo
 */
public final class AncientHolocron extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("multicolored spells");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public AncientHolocron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add one mana of any color to your manapool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1), new TapSourceCost()));

        // {T}: Add to mana of any color to your manapool. Spend this mana only to cast multicolored spells.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new ConditionalSpellManaBuilder(filter)));

    }

    private AncientHolocron(final AncientHolocron card) {
        super(card);
    }

    @Override
    public AncientHolocron copy() {
        return new AncientHolocron(this);
    }
}
