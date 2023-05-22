

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class StirringWildwood extends CardImpl {

    public StirringWildwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new StirringWildwoodToken(), CardType.LAND, Duration.EndOfTurn).withDurationRuleAtStart(true), new ManaCostsImpl<>("{1}{G}{W}")));
    }

    private StirringWildwood(final StirringWildwood card) {
        super(card);
    }

    @Override
    public StirringWildwood copy() {
        return new StirringWildwood(this);
    }

}

class StirringWildwoodToken extends TokenImpl {

    public StirringWildwoodToken() {
        super("", "3/4 green and white Elemental creature with reach");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setGreen(true);
        color.setWhite(true);
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(ReachAbility.getInstance());
    }
    public StirringWildwoodToken(final StirringWildwoodToken token) {
        super(token);
    }

    public StirringWildwoodToken copy() {
        return new StirringWildwoodToken(this);
    }

}
