
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WarriorToken;

/**
 *
 * @author TheElk801
 */
public final class BlaringRecruiter extends CardImpl {

    public BlaringRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Blaring Captain (When this creature enters the battlefield, target player may put Blaring Captain into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Blaring Captain"));

        // {2}{W}: Create a 1/1 white Warrior creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new WarriorToken()), new ManaCostsImpl<>("{2}{W}")));
    }

    private BlaringRecruiter(final BlaringRecruiter card) {
        super(card);
    }

    @Override
    public BlaringRecruiter copy() {
        return new BlaringRecruiter(this);
    }
}
