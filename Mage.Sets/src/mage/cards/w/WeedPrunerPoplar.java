
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class WeedPrunerPoplar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature other than {this}");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WeedPrunerPoplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, target creature other than Weed-Pruner Poplar gets -1/-1 until end of turn.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private WeedPrunerPoplar(final WeedPrunerPoplar card) {
        super(card);
    }

    @Override
    public WeedPrunerPoplar copy() {
        return new WeedPrunerPoplar(this);
    }
}
