package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WalkingSkyscraper extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Modified creatures you control", xValue);

    public WalkingSkyscraper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each modified creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Walking Skyscraper has hexproof as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                SourceTappedCondition.UNTAPPED, "{this} has hexproof as long as it's untapped"
        )));
    }

    private WalkingSkyscraper(final WalkingSkyscraper card) {
        super(card);
    }

    @Override
    public WalkingSkyscraper copy() {
        return new WalkingSkyscraper(this);
    }
}
