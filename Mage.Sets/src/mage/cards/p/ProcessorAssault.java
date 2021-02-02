
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ProcessorAssault extends CardImpl {

    public ProcessorAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // As an additional cost to cast Processor Assault, put a card an opponent owns from exile into its owner's graveyard.
        this.getSpellAbility().addCost(new ExileOpponentsCardFromExileToGraveyardCost(false));

        // Processor Assault deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ProcessorAssault(final ProcessorAssault card) {
        super(card);
    }

    @Override
    public ProcessorAssault copy() {
        return new ProcessorAssault(this);
    }
}
