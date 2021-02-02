
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
 * @author LevelX2
 */
public final class SymbioticWurm extends CardImpl {

    public SymbioticWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Symbiotic Wurm dies, create seven 1/1 green Insect creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new InsectToken(), 7)));

    }

    private SymbioticWurm(final SymbioticWurm card) {
        super(card);
    }

    @Override
    public SymbioticWurm copy() {
        return new SymbioticWurm(this);
    }
}
