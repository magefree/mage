
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.mana.RedManaAbility;
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
 * @author fireshoes
 */
public final class NeedleSpires extends CardImpl {

    public NeedleSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Needle Spires enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
        
        // {2}{R}{W}: Needle Spires becomes a 2/1 red and white Elemental creature with double strike until end of turn. It's still a land.
        Effect effect = new BecomesCreatureSourceEffect(new NeedleSpiresToken(), "land", Duration.EndOfTurn);
        effect.setText("{this} becomes a 2/1 red and white Elemental creature with double strike until end of turn. It's still a land");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{R}{W}")));
    }

    private NeedleSpires(final NeedleSpires card) {
        super(card);
    }

    @Override
    public NeedleSpires copy() {
        return new NeedleSpires(this);
    }
}

class NeedleSpiresToken extends TokenImpl {

    public NeedleSpiresToken() {
        super("", "2/1 red and white Elemental creature with double strike");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setRed(true);
        color.setWhite(true);
        power = new MageInt(2);
        toughness = new MageInt(1);
        addAbility(DoubleStrikeAbility.getInstance());
    }
    public NeedleSpiresToken(final NeedleSpiresToken token) {
        super(token);
    }

    public NeedleSpiresToken copy() {
        return new NeedleSpiresToken(this);
    }
}
