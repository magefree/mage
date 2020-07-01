package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedHallowblade extends CardImpl {

    public SeasonedHallowblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Discard a card: Tap Seasoned Hallowblade. It gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new TapSourceEffect(), new DiscardCardCost());
        ability.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains indestructible until end of turn"));
        this.addAbility(ability);
    }

    private SeasonedHallowblade(final SeasonedHallowblade card) {
        super(card);
    }

    @Override
    public SeasonedHallowblade copy() {
        return new SeasonedHallowblade(this);
    }
}
