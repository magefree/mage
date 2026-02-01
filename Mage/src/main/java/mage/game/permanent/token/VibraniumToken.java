package mage.game.permanent.token;

import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class VibraniumToken extends TokenImpl {

    public VibraniumToken() {
        super("Vibranium Token", "Vibranium token");
        cardType.add(CardType.ARTIFACT);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {C}. This mana can't be spent to cast a nonartifact spell.
        this.addAbility(new ConditionalColorlessManaAbility(1, new PowerstoneTokenManaBuilder()));
    }

    private VibraniumToken(final VibraniumToken token) {
        super(token);
    }

    public VibraniumToken copy() {
        return new VibraniumToken(this);
    }
}
