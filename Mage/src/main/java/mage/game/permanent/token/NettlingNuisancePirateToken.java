package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author Xanderhall
 */
public final class NettlingNuisancePirateToken extends TokenImpl {
    
    public NettlingNuisancePirateToken() {
        super("Pirate Token", "4/2 red Pirate creature token with \"This creature can't block.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PIRATE);
        color.setRed(true);
        power = new MageInt(4);
        toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(new CantBlockSourceEffect(Duration.EndOfGame)));
    }

    protected NettlingNuisancePirateToken(final NettlingNuisancePirateToken token) {
        super(token);
    }

    public NettlingNuisancePirateToken copy() {
        return new NettlingNuisancePirateToken(this);
    }
}
