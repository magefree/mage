package mage.cards.l;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class LightningSurge extends CardImpl {

    public LightningSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Lightning Surge deals 4 damage to any target.
        // Threshold - If seven or more cards are in your graveyard, instead Lightning Surge deals 6 damage to that creature or player and the damage can't be prevented.
        Effect effect = new ConditionalOneShotEffect(
                new DamageTargetEffect(6, false), new DamageTargetEffect(4),
                ThresholdCondition.instance, "{this} deals 4 damage to any target.<br>" +
                AbilityWord.THRESHOLD.formatWord() + "If seven or more cards are in your graveyard, " +
                "instead {this} deals 6 damage to that permanent or player and the damage can't be prevented"
        );
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(effect);

        // Flashback {5}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}{R}")));
    }

    private LightningSurge(final LightningSurge card) {
        super(card);
    }

    @Override
    public LightningSurge copy() {
        return new LightningSurge(this);
    }
}
