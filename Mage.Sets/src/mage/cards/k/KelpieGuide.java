package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KelpieGuide extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, ComparisonType.MORE_THAN, 7);

    public KelpieGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Untap another target permanent you control.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT));
        this.addAbility(ability);

        // {T}: Tap target permanent. Activate only if you control eight or more lands.
        ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost(), condition
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private KelpieGuide(final KelpieGuide card) {
        super(card);
    }

    @Override
    public KelpieGuide copy() {
        return new KelpieGuide(this);
    }
}
