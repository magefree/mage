
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactSpell;

/**
 *
 * @author LevelX2
 */
public final class MishrasWorkshop extends CardImpl {

    public MishrasWorkshop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}{C}{C}. Spend this mana only to cast artifact spells.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 3,
                new ConditionalSpellManaBuilder(new FilterArtifactSpell("artifact spells"))));

    }

    private MishrasWorkshop(final MishrasWorkshop card) {
        super(card);
    }

    @Override
    public MishrasWorkshop copy() {
        return new MishrasWorkshop(this);
    }
}
