package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class TibaltTheFiendBlooded extends CardImpl {

    public TibaltTheFiendBlooded(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIBALT);

        this.setStartingLoyalty(2);

        // +1: Draw a card, then discard a card at random.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        ability.addEffect(new DiscardControllerEffect(1, true).concatBy(", then"));
        this.addAbility(ability);

        // -4: Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player's hand to that player.
        Effect effect = new DamageTargetEffect(CardsInTargetHandCount.instance);
        effect.setText("{this} deals damage equal to the number of cards in target player's hand to that player");
        ability = new LoyaltyAbility(effect, -4);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -6: Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        ability = new LoyaltyAbility(new GainControlAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), -6);
        ability.addEffect(new UntapAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES).setText("untap them"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES,
                "they gain haste until end of turn"
        ));
        this.addAbility(ability);
    }

    private TibaltTheFiendBlooded(final TibaltTheFiendBlooded card) {
        super(card);
    }

    @Override
    public TibaltTheFiendBlooded copy() {
        return new TibaltTheFiendBlooded(this);
    }
}
