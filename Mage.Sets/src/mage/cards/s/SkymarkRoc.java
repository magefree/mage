

package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SkymarkRoc extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls with toughness 2 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.OR_LESS, 2));
    }

    public SkymarkRoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skymark Roc attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.
        Ability ability = new AttacksTriggeredAbility(new ReturnToHandTargetEffect(), true, null, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private SkymarkRoc(final SkymarkRoc card) {
        super(card);
    }

    @Override
    public SkymarkRoc copy() {
        return new SkymarkRoc(this);
    }
}
