package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public final class MazeOfIth extends CardImpl {
    
    public MazeOfIth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Untap target attacking creature. Prevent all combat damage that 
        // would be dealt to and dealt by that creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt to");
        effect.setOutcome(Outcome.Detriment);
        ability.addEffect(effect);
        effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        effect.setText("and dealt by that creature this turn");
        effect.setOutcome(Outcome.Detriment);
        ability.addEffect(effect);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }
    
    private MazeOfIth(final MazeOfIth card) {
        super(card);
    }
    
    @Override
    public MazeOfIth copy() {
        return new MazeOfIth(this);
    }
}
