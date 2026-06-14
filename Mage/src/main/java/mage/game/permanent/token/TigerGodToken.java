package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author muz
 */
public final class TigerGodToken extends TokenImpl {

    public TigerGodToken() {
        super("The Tiger God", "The Tiger God, a legendary 4/4 green Cat God creature token with \"The Tiger God can't be blocked by more than one creature.\"");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.GOD);

        this.color.setGreen(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()));
    }

    private TigerGodToken(final TigerGodToken token) {
        super(token);
    }

    public TigerGodToken copy() {
        return new TigerGodToken(this);
    }

}
