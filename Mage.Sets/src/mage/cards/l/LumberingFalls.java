
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author fireshoes
 */
public final class LumberingFalls extends CardImpl {

    public LumberingFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Lumbering Falls enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {U}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new GreenManaAbility());

        // {2}{G}{U}: Lumbering Falls becomes a 3/3 green and blue Elemental creature with hexproof until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new LumberingFallsToken(), "land", Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}{U}")));
    }

    private LumberingFalls(final LumberingFalls card) {
        super(card);
    }

    @Override
    public LumberingFalls copy() {
        return new LumberingFalls(this);
    }
}

class LumberingFallsToken extends TokenImpl {

    public LumberingFallsToken() {
        super("", "3/3 green and blue Elemental creature with hexproof");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setBlue(true);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(HexproofAbility.getInstance());
    }
    public LumberingFallsToken(final LumberingFallsToken token) {
        super(token);
    }

    public LumberingFallsToken copy() {
        return new LumberingFallsToken(this);
    }
}
