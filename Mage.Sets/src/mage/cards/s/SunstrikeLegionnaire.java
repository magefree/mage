
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SunstrikeLegionnaire extends CardImpl {

    private static final FilterCreaturePermanent untapFilter = new FilterCreaturePermanent("another creature");
    private static final FilterCreaturePermanent tapFilter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        untapFilter.add(AnotherPredicate.instance);
        tapFilter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SunstrikeLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sunstrike Legionnaire doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // Whenever another creature enters the battlefield, untap Sunstrike Legionnaire.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), untapFilter, false));
        // {tap}: Tap target creature with converted mana cost 3 or less.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(tapFilter));
        this.addAbility(ability);
    }

    private SunstrikeLegionnaire(final SunstrikeLegionnaire card) {
        super(card);
    }

    @Override
    public SunstrikeLegionnaire copy() {
        return new SunstrikeLegionnaire(this);
    }
}
