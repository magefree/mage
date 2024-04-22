package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PlantWarriorToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class YumaProudProtector extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND, 1);
    private static final Hint hint = new ValueHint("Lands in your graveyard", xValue);

    private static final FilterCard filter = new FilterCard("a Desert card");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public YumaProudProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell costs {1} less to cast for each land card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Whenever Yuma, Proud Protector enters the battlefield or attacks, you may sacrifice a land. If you do, draw a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new SacrificeTargetCost(StaticFilters.FILTER_LAND))
        ));

        // Whenever a Desert card is put into your graveyard from anywhere, create a 4/2 green Plant Warrior creature token with reach.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new CreateTokenEffect(new PlantWarriorToken()), false, filter, TargetController.YOU
        ));
    }

    private YumaProudProtector(final YumaProudProtector card) {
        super(card);
    }

    @Override
    public YumaProudProtector copy() {
        return new YumaProudProtector(this);
    }
}
