package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author spjspj
 */
public final class SpyMasterGoblinToken extends TokenImpl {

    public SpyMasterGoblinToken() {
        super("Goblin Token", "1/1 red Goblin creature token with \"Creatures you control attack each combat if able.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES)));
    }

    public SpyMasterGoblinToken(final SpyMasterGoblinToken token) {
        super(token);
    }

    public SpyMasterGoblinToken copy() {
        return new SpyMasterGoblinToken(this);
    }
}
