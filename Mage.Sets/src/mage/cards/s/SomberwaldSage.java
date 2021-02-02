
package mage.cards.s;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 * @author noxx
 */
public final class SomberwaldSage extends CardImpl {

    public SomberwaldSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}: Add three mana of any one color. Spend this mana only to cast creature spells.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 3, new SomberwaldSageManaBuilder(), true));
    }

    private SomberwaldSage(final SomberwaldSage card) {
        super(card);
    }

    @Override
    public SomberwaldSage copy() {
        return new SomberwaldSage(this);
    }
}

class SomberwaldSageManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}
