package mage.cards.g;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Grief extends CardImpl {

    private static final FilterCard filter = new FilterCard("a black card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Grief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Grief enters the battlefield, target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND, TargetController.OPPONENT)
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Evoke—Exile a black card from your hand.
        this.addAbility(new EvokeAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
    }

    private Grief(final Grief card) {
        super(card);
    }

    @Override
    public Grief copy() {
        return new Grief(this);
    }
}
