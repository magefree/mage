package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AvatarToken;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AjaniGoldmane extends CardImpl {

    public AjaniGoldmane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +1: You gain 2 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(2), 1));

        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        Ability ability = new LoyaltyAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), -1);
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("Those creatures gain vigilance until end of turn"));
        this.addAbility(ability);

        // -6: Create a white Avatar creature token. It has "This creature's power and toughness are each equal to your life total."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AvatarToken()), -6));
    }

    private AjaniGoldmane(final AjaniGoldmane card) {
        super(card);
    }

    @Override
    public AjaniGoldmane copy() {
        return new AjaniGoldmane(this);
    }
}
