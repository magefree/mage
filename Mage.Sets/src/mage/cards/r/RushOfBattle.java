
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class RushOfBattle extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    static {
        filter.add(SubType.WARRIOR.getPredicate());
    }
    
    public RushOfBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");


        // Creatures you control get +2/+1 until end of turn. Warrior creatures you control gain lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        Effect effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("Warrior creatures you control gain lifelink until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private RushOfBattle(final RushOfBattle card) {
        super(card);
    }

    @Override
    public RushOfBattle copy() {
        return new RushOfBattle(this);
    }
}
