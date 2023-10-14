
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.CatWarriorToken;

/**
 *
 * @author fireshoes
 */
public final class JeditOjanenOfEfrava extends CardImpl {

    public JeditOjanenOfEfrava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());

        // Whenever Jedit Ojanen of Efrava attacks or blocks, create a 2/2 green Cat Warrior creature token with forestwalk.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new CatWarriorToken()), false));
    }

    private JeditOjanenOfEfrava(final JeditOjanenOfEfrava card) {
        super(card);
    }

    @Override
    public JeditOjanenOfEfrava copy() {
        return new JeditOjanenOfEfrava(this);
    }
}
