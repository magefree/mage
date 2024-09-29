package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class EldraziAngelToken extends TokenImpl {

    public EldraziAngelToken() {
        super("Eldrazi Angel", "4/4 colorless Eldrazi Angel creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI, SubType.ANGEL);
        power = new MageInt(4);
        toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private EldraziAngelToken(final EldraziAngelToken token) {
        super(token);
    }

    @Override
    public EldraziAngelToken copy() {
        return new EldraziAngelToken(this);
    }
}
