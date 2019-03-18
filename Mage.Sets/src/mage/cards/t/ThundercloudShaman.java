
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class ThundercloudShaman extends CardImpl {

    private static final FilterCreaturePermanent filterGiants = new FilterCreaturePermanent("equal to the number of Giants you control");
    private static final FilterCreaturePermanent filterNonGiants = new FilterCreaturePermanent("non-Giant creature");
    static {
        filterGiants.add(new ControllerPredicate(TargetController.YOU));
        filterGiants.add(new SubtypePredicate(SubType.GIANT));
        filterNonGiants.add(Predicates.not(new SubtypePredicate(SubType.GIANT)));
    }

    public ThundercloudShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Thundercloud Shaman enters the battlefield, it deals damage equal to the number of Giants you control to each non-Giant creature.
        Effect effect = new DamageAllEffect(new PermanentsOnBattlefieldCount(filterGiants),filterNonGiants);
        effect.setText("it deals damage equal to the number of Giants you control to each non-Giant creature");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    public ThundercloudShaman(final ThundercloudShaman card) {
        super(card);
    }

    @Override
    public ThundercloudShaman copy() {
        return new ThundercloudShaman(this);
    }
}
