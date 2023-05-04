package mage.game.permanent.token.custom;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Token builder for token effects
 * <p>
 * Use it for custom tokens (tokens without public class and image)
 *
 * @author JayDi85
 */
public final class CreatureToken extends TokenImpl {

    public CreatureToken() {
        this(0, 0);
    }

    public CreatureToken(int power, int toughness) {
        this(power, toughness, String.format("%d/%d creature", power, toughness));
    }

    public CreatureToken(int power, int toughness, String description) {
        this(power, toughness, description, (SubType[]) null);
    }

    public CreatureToken(int power, int toughness, String description, SubType... extraSubTypes) {
        super("", description);
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

        if (extraSubTypes != null) {
            this.subtype.addAll(Arrays.asList(extraSubTypes));
        }
    }

    public CreatureToken withName(String name) {
        this.setName(name);
        return this;
    }

    public CreatureToken withAbility(Ability ability) {
        this.addAbility(ability);
        return this;
    }

    public CreatureToken withColor(String extraColors) {
        ObjectColor extraColorsList = new ObjectColor(extraColors);
        this.getColor(null).addColor(extraColorsList);
        return this;
    }

    public CreatureToken withType(CardType extraType) {
        if (!this.cardType.contains(extraType)) {
            this.cardType.add(extraType);
        }
        return this;
    }

    public CreatureToken withSubType(SubType extraSubType) {
        if (!this.subtype.contains(extraSubType)) {
            this.subtype.add(extraSubType);
        }
        return this;
    }

    public CreatureToken(final CreatureToken token) {
        super(token);
    }

    public CreatureToken copy() {
        return new CreatureToken(this);
    }

    private static String AutoGenerateTokenName(Token token) {
        // 111.4. A spell or ability that creates a token sets both its name and its subtype(s).
        // If the spell or ability doesn’t specify the name of the token,
        // its name is the same as its subtype(s) plus the word “Token.”
        // Once a token is on the battlefield, changing its name doesn’t change its subtype(s), and vice versa.
        return token.getSubtype()
                .stream()
                .map(SubType::getDescription)
                .collect(Collectors.joining(" ")) + " Token";
    }

    @Override
    public String getName() {
        String name = super.getName();
        if (name.isEmpty()) {
            name = AutoGenerateTokenName(this);
        }
        return name;
    }
}
