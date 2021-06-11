
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PhyrexianBloodstock extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PhyrexianBloodstock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Phyrexian Bloodstock leaves the battlefield, destroy target white creature. It can't be regenerated.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new DestroyTargetEffect(true), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private PhyrexianBloodstock(final PhyrexianBloodstock card) {
        super(card);
    }

    @Override
    public PhyrexianBloodstock copy() {
        return new PhyrexianBloodstock(this);
    }
}
