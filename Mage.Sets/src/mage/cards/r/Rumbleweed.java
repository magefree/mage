package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Rumbleweed extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND, 1);
    private static final Hint hint = new ValueHint("Lands in your graveyard", xValue);

    public Rumbleweed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each land card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Rumbleweed enters the battlefield, other creatures you control get +3/+3 and gain trample until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                3, 3, Duration.EndOfTurn, true
        ).setText("other creatures you control get +3/+3"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);
    }

    private Rumbleweed(final Rumbleweed card) {
        super(card);
    }

    @Override
    public Rumbleweed copy() {
        return new Rumbleweed(this);
    }
}
