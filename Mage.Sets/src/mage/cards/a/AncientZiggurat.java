
package mage.cards.a;

import java.util.UUID;
import mage.ConditionalMana;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class AncientZiggurat extends CardImpl {

    public AncientZiggurat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add one mana of any color. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new AncientZigguratManaBuilder()));
    }

    private AncientZiggurat(final AncientZiggurat card) {
        super(card);
    }

    @Override
    public AncientZiggurat copy() {
        return new AncientZiggurat(this);
    }
}


class AncientZigguratManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}


