
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class LlanowarEnvoy extends CardImpl {

    public LlanowarEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{G}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new ManaCostsImpl<>("{1}{G}"));
        this.addAbility(ability);
    }

    private LlanowarEnvoy(final LlanowarEnvoy card) {
        super(card);
    }

    @Override
    public LlanowarEnvoy copy() {
        return new LlanowarEnvoy(this);
    }
}
