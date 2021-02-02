
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox
 */
public final class HonorableScout extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black and/or red creature");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public HonorableScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Honorable Scout enters the battlefield, you gain 2 life for each black and/or red creature target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new PermanentsTargetOpponentControlsCount(filter, 2)));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HonorableScout(final HonorableScout card) {
        super(card);
    }

    @Override
    public HonorableScout copy() {
        return new HonorableScout(this);
    }
}
