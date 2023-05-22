
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
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
 * @author Plopman
 */
public final class GuardianIdol extends CardImpl {

    public GuardianIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Guardian Idol enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}: Guardian Idol becomes a 2/2 Golem artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GuardianIdolGolemToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private GuardianIdol(final GuardianIdol card) {
        super(card);
    }

    @Override
    public GuardianIdol copy() {
        return new GuardianIdol(this);
    }
}

class GuardianIdolGolemToken extends TokenImpl {

    public GuardianIdolGolemToken() {
        super("Golem", "2/2 Golem artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
    public GuardianIdolGolemToken(final GuardianIdolGolemToken token) {
        super(token);
    }

    public GuardianIdolGolemToken copy() {
        return new GuardianIdolGolemToken(this);
    }
}
