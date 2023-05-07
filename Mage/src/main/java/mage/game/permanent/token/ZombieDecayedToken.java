package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DecayedAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ZombieDecayedToken extends TokenImpl {

    public ZombieDecayedToken() {
        super("Zombie Token", "2/2 black Zombie creature token with decayed");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new DecayedAbility());
    }

    public ZombieDecayedToken(final ZombieDecayedToken token) {
        super(token);
    }

    @Override
    public ZombieDecayedToken copy() {
        return new ZombieDecayedToken(this);
    }
}
