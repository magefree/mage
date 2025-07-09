package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EdificeOfAuthority extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BRICK, 3);

    public EdificeOfAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}, {T}: Target creature can't attack this turn. Put a brick counter on Edifice of Authority.
        Ability ability = new SimpleActivatedAbility(new CantAttackTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.BRICK.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {1}, {T}: Until your next turn, target creature can't attack or block and its activated abilities can't be activated. Activate this ability only if there are three or more brick counter on Edifice of Authority.
        Ability ability2 = new ActivateIfConditionActivatedAbility(new EdificeOfAuthorityEffect(), new GenericManaCost(1), condition);
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private EdificeOfAuthority(final EdificeOfAuthority card) {
        super(card);
    }

    @Override
    public EdificeOfAuthority copy() {
        return new EdificeOfAuthority(this);
    }
}

class EdificeOfAuthorityEffect extends RestrictionEffect {

    EdificeOfAuthorityEffect() {
        super(Duration.UntilYourNextTurn);
        staticText = "until your next turn, target creature can't attack or block and its activated abilities can't be activated";
    }

    private EdificeOfAuthorityEffect(final EdificeOfAuthorityEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addInfo("Can't attack or block and its activated abilities can't be activated." + getId(), "", game);
            }
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public EdificeOfAuthorityEffect copy() {
        return new EdificeOfAuthorityEffect(this);
    }

}
