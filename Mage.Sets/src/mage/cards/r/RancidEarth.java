package mage.cards.r;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class RancidEarth extends CardImpl {

    public RancidEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());

        // Threshold - If seven or more cards are in your graveyard, instead destroy that land and Rancid Earth deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE),
                ThresholdCondition.instance, "<br>" + AbilityWord.THRESHOLD.formatWord() + "If seven or more " +
                "cards are in your graveyard, instead destroy that land and {this} deals 1 damage to each creature"
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamagePlayersEffect(1), ThresholdCondition.instance, "and each player"
        ));
    }

    private RancidEarth(final RancidEarth card) {
        super(card);
    }

    @Override
    public RancidEarth copy() {
        return new RancidEarth(this);
    }
}
