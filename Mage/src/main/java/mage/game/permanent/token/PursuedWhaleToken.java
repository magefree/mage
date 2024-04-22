package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class PursuedWhaleToken extends TokenImpl {

    public PursuedWhaleToken() {
        super("Pirate Token", "1/1 red Pirate creature token with \"This creature can't block\" and \"Creatures you control attack each combat if able.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.PIRATE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new CantBlockSourceEffect(Duration.WhileOnBattlefield)
                .setText("this creature can't block")));
        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES)));
    }

    private PursuedWhaleToken(final PursuedWhaleToken token) {
        super(token);
    }

    @Override
    public PursuedWhaleToken copy() {
        return new PursuedWhaleToken(this);
    }
}
