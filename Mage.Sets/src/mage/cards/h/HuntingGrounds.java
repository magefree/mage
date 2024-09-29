package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HuntingGrounds extends CardImpl {

    public HuntingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{W}");

        // Threshold - As long as seven or more cards are in your graveyard, Hunting Grounds has "Whenever an opponent casts a spell, you may put a creature card from your hand onto the battlefield."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SpellCastOpponentTriggeredAbility(
                        new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A), true
                ), Duration.WhileOnBattlefield), ThresholdCondition.instance, "as long as seven or more " +
                "cards are in your graveyard, {this} has \"Whenever an opponent casts a spell, " +
                "you may put a creature card from your hand onto the battlefield.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private HuntingGrounds(final HuntingGrounds card) {
        super(card);
    }

    @Override
    public HuntingGrounds copy() {
        return new HuntingGrounds(this);
    }
}
