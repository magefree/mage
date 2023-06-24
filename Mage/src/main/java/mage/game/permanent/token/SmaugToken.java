package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class SmaugToken extends TokenImpl {

    public SmaugToken() {
        super("Smaug", "Smaug, a legendary 6/6 red Dragon creature token with flying, haste, and \"When this creature dies, create fourteen Treasure tokens.\"");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.DRAGON);
        this.color.setRed(true);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 14)
        ).setTriggerPhrase("When this creature dies, "));
    }

    public SmaugToken(final SmaugToken token) {
        super(token);
    }

    @Override
    public SmaugToken copy() {
        return new SmaugToken(this);
    }
}
