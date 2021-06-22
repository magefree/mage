package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoungNecromancer extends CardImpl {

    public YoungNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Young Necromancer enters the battlefield, you may exile two cards from your graveyard. When you do, return target creature card from your graveyard to the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false,
                "return target creature card from your graveyard to the battlefield"
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(ability, new ExileFromGraveCost(
                        new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CARDS)
                ), "Exile two cards from your graveyard?")
        ));
    }

    private YoungNecromancer(final YoungNecromancer card) {
        super(card);
    }

    @Override
    public YoungNecromancer copy() {
        return new YoungNecromancer(this);
    }
}
