

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DreadStatuary extends CardImpl {

    public DreadStatuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new DreadStatuaryToken(), CardType.LAND, Duration.EndOfTurn), new ManaCostsImpl<>("{4}")));
    }

    private DreadStatuary(final DreadStatuary card) {
        super(card);
    }

    @Override
    public DreadStatuary copy() {
        return new DreadStatuary(this);
    }

}

class DreadStatuaryToken extends TokenImpl {

    public DreadStatuaryToken() {
        super("", "4/2 Golem artifact creature");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.GOLEM);
        power = new MageInt(4);
        toughness = new MageInt(2);
    }
    public DreadStatuaryToken(final DreadStatuaryToken token) {
        super(token);
    }

    public DreadStatuaryToken copy() {
        return new DreadStatuaryToken(this);
    }
}
