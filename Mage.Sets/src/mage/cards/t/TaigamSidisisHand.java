package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class TaigamSidisisHand extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);

    public TaigamSidisisHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // At the beginning of your upkeep, look at the top three cards of your library.
        // Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.GRAVEYARD),
                TargetController.YOU, false));

        // {B}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TaigamSidisisHand(final TaigamSidisisHand card) {
        super(card);
    }

    @Override
    public TaigamSidisisHand copy() {
        return new TaigamSidisisHand(this);
    }
}
