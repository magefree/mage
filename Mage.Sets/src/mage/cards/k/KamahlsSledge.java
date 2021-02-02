
package mage.cards.k;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class KamahlsSledge extends CardImpl {

    public KamahlsSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");

        // Kamahl's Sledge deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Threshold - If seven or more cards are in your graveyard, instead Kamahl's Sledge deals 4 damage to that creature and 4 damage to that creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetControllerEffect(4),
            new CardsInControllerGraveyardCondition(7),
            "<br><br><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, instead {this} deals 4 damage to that creature and 4 damage to that creature's controller."));
    }

    private KamahlsSledge(final KamahlsSledge card) {
        super(card);
    }

    @Override
    public KamahlsSledge copy() {
        return new KamahlsSledge(this);
    }
}
