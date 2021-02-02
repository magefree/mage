
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.GodSireBeastToken;

/**
 *
 * @author Loki
 */
public final class Godsire extends CardImpl {

    public Godsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}{G}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GodSireBeastToken()), new TapSourceCost()));
    }

    private Godsire(final Godsire card) {
        super(card);
    }

    @Override
    public Godsire copy() {
        return new Godsire(this);
    }

}
