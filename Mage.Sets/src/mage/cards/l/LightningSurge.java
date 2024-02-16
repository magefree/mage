
package mage.cards.l;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class LightningSurge extends CardImpl {

    public LightningSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Lightning Surge deals 4 damage to any target.
        // Threshold - If seven or more cards are in your graveyard, instead Lightning Surge deals 6 damage to that creature or player and the damage can't be prevented.
        Effect effect = new ConditionalOneShotEffect(new DamageTargetEffect(6, false),
                new DamageTargetEffect(4),
                new CardsInControllerGraveyardCondition(7),
                "{this} deals 4 damage to any target.<br/><br/><i>Threshold</i> &mdash; {this} deals 6 damage to that permanent or player and the damage can't be prevented instead if seven or more cards are in your graveyard.");
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
