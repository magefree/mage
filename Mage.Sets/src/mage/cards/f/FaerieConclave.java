
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Loki
 */
public final class FaerieConclave extends CardImpl {

    public FaerieConclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new FaerieConclaveToken(), "land", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")));
    }

    private FaerieConclave(final FaerieConclave card) {
        super(card);
    }

    @Override
    public FaerieConclave copy() {
        return new FaerieConclave(this);
    }
}

class FaerieConclaveToken extends TokenImpl {
    FaerieConclaveToken() {
        super("Faerie", "2/1 blue Faerie creature with flying");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.FAERIE);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
    public FaerieConclaveToken(final FaerieConclaveToken token) {
        super(token);
    }

    public FaerieConclaveToken copy() {
        return new FaerieConclaveToken(this);
    }
}
