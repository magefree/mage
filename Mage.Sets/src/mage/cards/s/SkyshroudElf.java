
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author noxx
 */
public final class SkyshroudElf extends CardImpl {

    public SkyshroudElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {1}: Add {R} or {W}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new ManaCostsImpl<>("{1}"));
        this.addAbility(ability);
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new ManaCostsImpl<>("{1}"));
        this.addAbility(ability2);
    }

    private SkyshroudElf(final SkyshroudElf card) {
        super(card);
    }

    @Override
    public SkyshroudElf copy() {
        return new SkyshroudElf(this);
    }
}
