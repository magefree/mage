package mage.game.permanent.token;

import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class HeartwoodToken extends TokenImpl {

    public HeartwoodToken() {
        super("Heartwood Token", "red and green artifact token with \"{T}: Add {R} or {G}.\"");
        cardType.add(CardType.ARTIFACT);
        color.setRed(true);
        color.setGreen(true);

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private HeartwoodToken(final HeartwoodToken token) {
        super(token);
    }

    @Override
    public HeartwoodToken copy() {
        return new HeartwoodToken(this);
    }
}
