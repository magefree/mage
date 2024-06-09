
package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class YukoraThePrisoner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Ogre creatures");

    static {
        filter.add(Predicates.not(SubType.OGRE.getPredicate()));
    }

    public YukoraThePrisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Yukora, the Prisoner leaves the battlefield, sacrifice all non-Ogre creatures you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SacrificeAllControllerEffect(filter), false));

    }

    private YukoraThePrisoner(final YukoraThePrisoner card) {
        super(card);
    }

    @Override
    public YukoraThePrisoner copy() {
        return new YukoraThePrisoner(this);
    }
}