package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class ScrybSpritesToken extends TokenImpl {

    public ScrybSpritesToken() {
        super("Scryb Sprites", "Scryb Sprites token");
        manaCost = new ManaCostsImpl<>("{G}");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FAERIE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ScrybSpritesToken(final ScrybSpritesToken token) {
        super(token);
    }

    @Override
    public ScrybSpritesToken copy() {
        return new ScrybSpritesToken(this);
    }
}
