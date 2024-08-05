package mage.cards.k;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class KamahlsSledge extends CardImpl {

    public KamahlsSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Kamahl's Sledge deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Threshold - If seven or more cards are in your graveyard, instead Kamahl's Sledge deals 4 damage to that creature and 4 damage to that creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(4), ThresholdCondition.instance, "<br>" +
                AbilityWord.THRESHOLD.formatWord() + "If seven or more cards are in your graveyard, " +
                "instead {this} deals 4 damage to that creature and 4 damage to that creature's controller."
        ));
    }

    private KamahlsSledge(final KamahlsSledge card) {
        super(card);
    }

    @Override
    public KamahlsSledge copy() {
        return new KamahlsSledge(this);
    }
}
