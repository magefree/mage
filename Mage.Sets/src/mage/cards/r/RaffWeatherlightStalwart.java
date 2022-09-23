package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaffWeatherlightStalwart extends CardImpl {

    public RaffWeatherlightStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell, you may tap two untapped creatures you control. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new TapTargetCost(new TargetControlledPermanent(
                                2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                        ))
                ), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {3}{W}{W}: Creatures you control get +1/+1 and gain vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                new ManaCostsImpl<>("{3}{W}{W}")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability);
    }

    private RaffWeatherlightStalwart(final RaffWeatherlightStalwart card) {
        super(card);
    }

    @Override
    public RaffWeatherlightStalwart copy() {
        return new RaffWeatherlightStalwart(this);
    }
}
