package mage.game.permanent.token;

import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class HeartwoodToken extends TokenImpl {

    public HeartwoodToken() {
        super("Heartwood Token", "Heartwood token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.HEARTWOOD);
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
