package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class ScorpionDragonToken extends TokenImpl {

    public ScorpionDragonToken() {
        super("Scorpion Dragon Token", "4/4 red Scorpion Dragon creature token with flying and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SCORPION);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private ScorpionDragonToken(final ScorpionDragonToken token) {
        super(token);
    }

    @Override
    public ScorpionDragonToken copy() {
        return new ScorpionDragonToken(this);
    }
}
