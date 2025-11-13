package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;

public final class VrondissRageOfAncientsToken extends TokenImpl {

    public VrondissRageOfAncientsToken() {
        super("Dragon Spirit Token", "5/4 red and green Dragon Spirit creature token with \"When this creature deals damage, sacrifice it.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(5);
        toughness = new MageInt(4);

        this.addAbility(new DealsCombatDamageTriggeredAbility(new SacrificeSourceEffect(), false));
    }

    private VrondissRageOfAncientsToken(final VrondissRageOfAncientsToken token) {
        super(token);
    }

    public VrondissRageOfAncientsToken copy() {
        return new VrondissRageOfAncientsToken(this);
    }
}
