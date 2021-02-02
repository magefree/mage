
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author LoneFox
 */
public final class SymbioticElf extends CardImpl {

    public SymbioticElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Symbiotic Elf dies, create two 1/1 green Insect creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new InsectToken(), 2)));
    }

    private SymbioticElf(final SymbioticElf card) {
        super(card);
    }

    @Override
    public SymbioticElf copy() {
        return new SymbioticElf(this);
    }
}
