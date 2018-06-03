
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SpinEngine extends CardImpl {

    public SpinEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {R}: Target creature can't block Spin Engine this turn
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpinEngineEffect(), new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SpinEngine(final SpinEngine card) {
        super(card);
    }

    @Override
    public SpinEngine copy() {
        return new SpinEngine(this);
    }

}

class SpinEngineEffect extends RestrictionEffect {

    public SpinEngineEffect() {
        super(Duration.EndOfTurn);
        staticText = "Target creature can't block {this} this turn";
    }

    public SpinEngineEffect(final SpinEngineEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        UUID targetId = source.getFirstTarget();
        if (targetId != null && blocker.getId().equals(targetId))
            return false;
        return true;
    }

    @Override
    public SpinEngineEffect copy() {
        return new SpinEngineEffect(this);
    }

}
