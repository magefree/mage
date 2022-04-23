
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TuktukTheReturnedToken;

/**
 *
 * @author Loki
 */
public final class TuktukTheExplorer extends CardImpl {

    public TuktukTheExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TuktukTheReturnedToken())));
    }

    private TuktukTheExplorer(final TuktukTheExplorer card) {
        super(card);
    }

    @Override
    public TuktukTheExplorer copy() {
        return new TuktukTheExplorer(this);
    }

}
