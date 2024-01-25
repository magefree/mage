package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PlantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsidiousRoots extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PLANT);

    public InsidiousRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}");

        // Creature tokens you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // Whenever one or more creature cards leave your graveyard, create a 0/1 green Plant creature token, then put a +1/+1 counter on each Plant you control.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new PlantToken()), StaticFilters.FILTER_CARD_CREATURE
        );
        ability.addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), filter
        ).concatBy(", then"));
        this.addAbility(ability);
    }

    private InsidiousRoots(final InsidiousRoots card) {
        super(card);
    }

    @Override
    public InsidiousRoots copy() {
        return new InsidiousRoots(this);
    }
}
