package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;
import mage.target.targetadjustment.ManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResonanceTechnician extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("untapped artifacts you control");
    private static final FilterSpell filter2 = new FilterInstantOrSorcerySpell("instant or sorcery spell you control with mana value X");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ResonanceTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/R}{U/R}");

        this.subtype.add(SubType.WEIRD);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Resonance Technician enters the battlefield, you may discard a card. If you do, investigate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new InvestigateEffect(2), new DiscardCardCost())
        ));

        // {T}, Tap X untapped artifacts you control: Copy target instant or sorcery spell you control with mana value X. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect(), new TapSourceCost());
        ability.addCost(new TapVariableTargetCost(filter));
        ability.addTarget(new TargetSpell(filter2));
        ability.setTargetAdjuster(new ManaValueTargetAdjuster(GetXValue.instance, ComparisonType.EQUAL_TO));
        this.addAbility(ability);
    }

    private ResonanceTechnician(final ResonanceTechnician card) {
        super(card);
    }

    @Override
    public ResonanceTechnician copy() {
        return new ResonanceTechnician(this);
    }
}
