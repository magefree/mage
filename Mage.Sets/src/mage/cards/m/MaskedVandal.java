package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskedVandal extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MaskedVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When enters the battlefield, you may exile a creature card from your graveyard. If you do, exile target artifact or enchantment an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ExileTargetEffect(), new ExileFromGraveCost(
                        new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)
                ))
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MaskedVandal(final MaskedVandal card) {
        super(card);
    }

    @Override
    public MaskedVandal copy() {
        return new MaskedVandal(this);
    }
}
