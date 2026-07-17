package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NehebTheWorthy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MINOTAUR, "Minotaurs");

    public NehebTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Other Minotaurs you control have first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // As long as you have one or fewer cards in hand, Minotaurs you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter),
                HeckbentCondition.instance, "as long as you have one or fewer cards in hand, Minotaurs you control get +2/+0"
        )));

        // Whenever Neheb, the Worthy deals combat damage to a player, each player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardEachPlayerEffect(), false));
    }

    private NehebTheWorthy(final NehebTheWorthy card) {
        super(card);
    }

    @Override
    public NehebTheWorthy copy() {
        return new NehebTheWorthy(this);
    }
}
