
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author North
 */
public final class GhituEncampment extends CardImpl {

    public GhituEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new GhituEncampmentToken(), "land", Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{R}")));
    }

    private GhituEncampment(final GhituEncampment card) {
        super(card);
    }

    @Override
    public GhituEncampment copy() {
        return new GhituEncampment(this);
    }
}

class GhituEncampmentToken extends TokenImpl {

    public GhituEncampmentToken() {
        super("Warrior", "2/1 red Warrior creature with first strike");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.WARRIOR);

        this.color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
    }
    public GhituEncampmentToken(final GhituEncampmentToken token) {
        super(token);
    }

    public GhituEncampmentToken copy() {
        return new GhituEncampmentToken(this);
    }
}
