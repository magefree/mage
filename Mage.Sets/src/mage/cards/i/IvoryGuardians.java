
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class IvoryGuardians extends CardImpl {

    private static final FilterPermanent controlFilter = new FilterPermanent("nontoken red permanent");
    private static final FilterCreaturePermanent boostFilter = new FilterCreaturePermanent();

    static {
        controlFilter.add(new ColorPredicate(ObjectColor.RED));
        controlFilter.add(TokenPredicate.FALSE);
        boostFilter.add(new NamePredicate("Ivory Guardians"));
    }

    public IvoryGuardians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // Creatures named Ivory Guardians get +1/+1 as long as an opponent controls a nontoken red permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, boostFilter, false),
                new OpponentControlsPermanentCondition(controlFilter),
                "Creatures named Ivory Guardians get +1/+1 as long as an opponent controls a nontoken red permanent")));
    }

    private IvoryGuardians(final IvoryGuardians card) {
        super(card);
    }

    @Override
    public IvoryGuardians copy() {
        return new IvoryGuardians(this);
    }
}
