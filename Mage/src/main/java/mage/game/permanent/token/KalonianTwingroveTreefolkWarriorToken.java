package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author spjspj
 */
public final class KalonianTwingroveTreefolkWarriorToken extends TokenImpl {

    static final FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(SubType.FOREST.getPredicate());
    }

    public KalonianTwingroveTreefolkWarriorToken() {
        super("Treefolk Warrior Token", "green Treefolk Warrior creature token with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TREEFOLK);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands))));
    }

    public KalonianTwingroveTreefolkWarriorToken(final KalonianTwingroveTreefolkWarriorToken token) {
        super(token);
    }

    public KalonianTwingroveTreefolkWarriorToken copy() {
        return new KalonianTwingroveTreefolkWarriorToken(this);
    }
}
