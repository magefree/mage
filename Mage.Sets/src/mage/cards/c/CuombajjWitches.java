
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class CuombajjWitches extends CardImpl {

    public CuombajjWitches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Cuombajj Witches deals 1 damage to any target and 1 damage to any target of an opponent's choice.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to any target and 1 damage to any target of an opponent's choice");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.addTarget(new TargetAnyTarget());
        ability.setTargetAdjuster(CuombajjWitchesAdjuster.instance);
        this.addAbility(ability);
    }

    private CuombajjWitches(final CuombajjWitches card) {
        super(card);
    }

    @Override
    public CuombajjWitches copy() {
        return new CuombajjWitches(this);
    }
}

enum CuombajjWitchesAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            UUID opponentId = null;
            if (game.getOpponents(controller.getId()).size() > 1) {
                Target target = new TargetOpponent(true);
                if (controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                    opponentId = target.getFirstTarget();
                }
            } else {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            }
            if (opponentId != null) {
                ability.getTargets().get(1).setTargetController(opponentId);
            }
        }
    }
}