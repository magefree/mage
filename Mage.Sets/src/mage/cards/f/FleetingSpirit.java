package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class FleetingSpirit extends CardImpl {

    public FleetingSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {W}, Exile three cards from your graveyard: Fleeting Spirit gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        this.addAbility(ability);

        // Discard a card: Exile Fleeting Spirit. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(), new DiscardCardCost()));
    }

    private FleetingSpirit(final FleetingSpirit card) {
        super(card);
    }

    @Override
    public FleetingSpirit copy() {
        return new FleetingSpirit(this);
    }
}
