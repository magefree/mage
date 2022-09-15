package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InquisitorGreyfax extends CardImpl {

    public InquisitorGreyfax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.INQUISITOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Unquestionable Wisdom -- Other creatures you control get +1/+0 and have vigilance.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE, true
        ).setText("and have vigilance"));
        this.addAbility(ability.withFlavorWord("Unquestionable Wisdom"));

        // Hunt for Heresy -- {1}, {T}: Tap target creature an opponent controls. Investigate.
        ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InvestigateEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Hunt for Heresy"));
    }

    private InquisitorGreyfax(final InquisitorGreyfax card) {
        super(card);
    }

    @Override
    public InquisitorGreyfax copy() {
        return new InquisitorGreyfax(this);
    }
}
