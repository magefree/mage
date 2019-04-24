

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author spjspj
 */
public final class KalonianTwingroveTreefolkWarriorToken extends TokenImpl {
    
    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(new SubtypePredicate(SubType.FOREST));
    }

    public KalonianTwingroveTreefolkWarriorToken() {
        super("Treefolk Warrior", "green Treefolk Warrior creature token with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TREEFOLK);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.WhileOnBattlefield)));
    }

    public KalonianTwingroveTreefolkWarriorToken(final KalonianTwingroveTreefolkWarriorToken token) {
        super(token);
    }

    public KalonianTwingroveTreefolkWarriorToken copy() {
        return new KalonianTwingroveTreefolkWarriorToken(this);
    }
}
