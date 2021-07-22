
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class DarkSalvation extends CardImpl {

    public DarkSalvation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{B}");

        // Target player creates X 2/2 black Zombie creature tokens, then up to one target creature gets -1/-1 until end of turn for each Zombie that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        Effect effect = new CreateTokenTargetEffect(new ZombieToken(), ManacostVariableValue.REGULAR);
        effect.setText("Target player creates X 2/2 black Zombie creature tokens");
        this.getSpellAbility().addEffect(effect);
        DynamicValue value = new ZombiesControlledByTargetPlayerCount();

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        effect = new BoostTargetEffect(value, value, Duration.EndOfTurn, true);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText(", then up to one target creature gets -1/-1 until end of turn for each Zombie that player controls");
        this.getSpellAbility().addEffect(effect);
    }

    private DarkSalvation(final DarkSalvation card) {
        super(card);
    }

    @Override
    public DarkSalvation copy() {
        return new DarkSalvation(this);
    }
}

class ZombiesControlledByTargetPlayerCount implements DynamicValue {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombies");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    @Override
    public ZombiesControlledByTargetPlayerCount copy() {
        return new ZombiesControlledByTargetPlayerCount();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getTargets().get(0).getFirstTarget());
        if (player != null) {
            int value = game.getBattlefield().countAll(filter, player.getId(), game);
            return -1 * value;
        } else {
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " that player controls";
    }
}
