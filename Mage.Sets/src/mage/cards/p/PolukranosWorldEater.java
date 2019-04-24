
package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * * The value of X in Polukranos's last ability is equal to the value chosen
 *   for X when its activated ability was activated.
 *
 * * The number of targets chosen for the triggered ability must be at least one
 *   (if X wasn't 0) and at most X. You choose the division of damage as you put
 *   the ability on the stack, not as it resolves. Each target must be assigned
 *   at least 1 damage. In multiplayer games, you may choose creatures controlled
 *   by different opponents.
 *
 * * If some, but not all, of the ability's targets become illegal, you can't change
 *   the division of damage. Damage that would've been dealt to illegal targets
 *   simply isn't dealt.
 *
 * * As Polukranos's triggered ability resolves, Polukranos deals damage first, then
 *   the target creatures do. Although no creature will die until after the ability
 *   finishes resolving, the order could matter if Polukranos has wither or infect.
 *
 * @author LevelX2
 */
public final class PolukranosWorldEater extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PolukranosWorldEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {X}{X}{G}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{X}{G}", Integer.MAX_VALUE));
        // When Polukranos, World Eater becomes monstrous, it deals X damage divided as you choose among any number of target creatures your opponents control. Each of those creatures deals damage equal to its power to Polukranos.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new PolukranosWorldEaterEffect());
        ability.addTarget(new TargetCreaturePermanentAmount(1, filter));
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof BecomesMonstrousSourceTriggeredAbility) {
            int xValue = ((BecomesMonstrousSourceTriggeredAbility) ability).getMonstrosityValue();
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanentAmount(xValue, filter));
        }
    }

    public PolukranosWorldEater(final PolukranosWorldEater card) {
        super(card);
    }

    @Override
    public PolukranosWorldEater copy() {
        return new PolukranosWorldEater(this);
    }
}

class PolukranosWorldEaterEffect extends OneShotEffect {

    public PolukranosWorldEaterEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals X damage divided as you choose among any number of target creatures your opponents control. Each of those creatures deals damage equal to its power to Polukranos";
    }

    public PolukranosWorldEaterEffect(final PolukranosWorldEaterEffect effect) {
        super(effect);
    }

    @Override
    public PolukranosWorldEaterEffect copy() {
        return new PolukranosWorldEaterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            Set<Permanent> permanents = new HashSet<>();
            for (UUID target: multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanents.add(permanent);
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true);
                }
            }
            // Each of those creatures deals damage equal to its power to Polukranos
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null) {
                for (Permanent permanent :permanents) {
                    sourceCreature.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
