
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class ZombieKnightToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }
    
    public ZombieKnightToken(){
        super("Zombie Knight Token", "a 2/2 black Zombie Knight creature token with menace");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode("DOM");
        color.setBlack(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ZOMBIE, SubType.KNIGHT);
        addAbility(new MenaceAbility());
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ZombieKnightToken(final ZombieKnightToken zombieKnightToken){
        super(zombieKnightToken);
    }

    @Override
    public ZombieKnightToken copy() {
        return new ZombieKnightToken(this);
    }
}
