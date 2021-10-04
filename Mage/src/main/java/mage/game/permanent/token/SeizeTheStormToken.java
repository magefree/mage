package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SeizeTheStormToken extends TokenImpl {

    public SeizeTheStormToken(DynamicValue xValue, Hint hint) {
        super("Elemental", "red Elemental creature token with trample and " +
                "\"This creature's power and toughness are each equal to the number of instant " +
                "and sorcery cards in your graveyard, plus the number of cards with flashback you own in exile.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new SetPowerToughnessSourceEffect(
                xValue, Duration.WhileOnBattlefield
        ).setText("this creature's power and toughness are each equal to the number of " +
                "instant and sorcery cards in your graveyard, plus the number of cards with flashback you own in exile")
        ).addHint(hint));
    }

    private SeizeTheStormToken(final SeizeTheStormToken token) {
        super(token);
    }

    @Override
    public SeizeTheStormToken copy() {
        return new SeizeTheStormToken(this);
    }
}
