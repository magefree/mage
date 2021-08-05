package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterSkald extends CardImpl {

    private static final FilterCard filter
            = new FilterArtifactOrEnchantmentCard("artifact or enchantment card from your graveyard");

    public MasterSkald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Master Skald enters the battlefield, you may exile a creature card from your graveyard. If you do, return target artifact or enchantment card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToHandTargetEffect(),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(
                        StaticFilters.FILTER_CARD_CREATURE_A
                ))
        ));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MasterSkald(final MasterSkald card) {
        super(card);
    }

    @Override
    public MasterSkald copy() {
        return new MasterSkald(this);
    }
}
