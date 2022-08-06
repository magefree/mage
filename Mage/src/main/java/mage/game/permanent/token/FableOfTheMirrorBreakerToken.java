package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public class FableOfTheMirrorBreakerToken extends TokenImpl {

    public FableOfTheMirrorBreakerToken() {
        super("Goblin Shaman Token", "2/2 red Goblin Shaman creature token with \"Whenever this creature attacks, create a Treasure token.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.SHAMAN);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())).setTriggerPhrase("Whenever this creature attacks, "));
        setOriginalExpansionSetCode("NEO");
    }

    private FableOfTheMirrorBreakerToken(final FableOfTheMirrorBreakerToken token) {
        super(token);
    }

    @Override
    public FableOfTheMirrorBreakerToken copy() {
        return new FableOfTheMirrorBreakerToken(this);
    }
}
