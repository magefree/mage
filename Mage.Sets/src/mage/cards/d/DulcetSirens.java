
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DulcetSirens extends CardImpl {

    public DulcetSirens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}, {T}: Target creature attacks target opponent this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DulcetSirensForceAttackEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponent());        
        this.addAbility(ability);


        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{U}")));
    }

    private DulcetSirens(final DulcetSirens card) {
        super(card);
    }

    @Override
    public DulcetSirens copy() {
        return new DulcetSirens(this);
    }
}

class DulcetSirensForceAttackEffect extends RequirementEffect {

    public DulcetSirensForceAttackEffect(Duration duration) {
        super(duration);
        staticText = "Target creature attacks target opponent this turn if able";
    }

    public DulcetSirensForceAttackEffect(final DulcetSirensForceAttackEffect effect) {
        super(effect);
    }

    @Override
    public DulcetSirensForceAttackEffect copy() {
        return new DulcetSirensForceAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (this.getTargetPointer().getTargets(game, source).contains(permanent.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        Target target = source.getTargets().get(1);
        if (target != null) {
            return target.getFirstTarget();
        }
        return null;
    }

}
