package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class AggressiveMutation extends CardImpl {

    public AggressiveMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");
        

        // X target creatures each get +X/+X until end of turn.
        Effect effect = new BoostTargetEffect(new ManacostVariableValue(), new ManacostVariableValue(), Duration.EndOfTurn);
        effect.setText("X target creatures each get +X/+X until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public AggressiveMutation(final AggressiveMutation card) {
        super(card);
    }

    @Override
    public AggressiveMutation copy() {
        return new AggressiveMutation(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetCreaturePermanent(xValue));
        }
    }
}
