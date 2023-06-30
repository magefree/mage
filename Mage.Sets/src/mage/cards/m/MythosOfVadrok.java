package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class MythosOfVadrok extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            ManaWasSpentCondition.WHITE,
            ManaWasSpentCondition.BLUE
    );

    public MythosOfVadrok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Mythos of Vadrok deals 5 damage divided as you choose among any number of target creatures and/or planeswalkers. If {W}{U} was spent to cast this spell, until your next turn, those permanents can't attack or block and their activated abilities can't be activated.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalkerAmount(5));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new MythosOfVadrokEffect(), condition, "If {W}{U} was spent to cast this spell, " +
                "until your next turn, those permanents can't attack or block " +
                "and their activated abilities can't be activated."
        ));
    }

    private MythosOfVadrok(final MythosOfVadrok card) {
        super(card);
    }

    @Override
    public MythosOfVadrok copy() {
        return new MythosOfVadrok(this);
    }
}

class MythosOfVadrokEffect extends OneShotEffect {

    MythosOfVadrokEffect() {
        super(Benefit);
    }

    private MythosOfVadrokEffect(final MythosOfVadrokEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfVadrokEffect copy() {
        return new MythosOfVadrokEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new MythosOfVadrokRestrictionEffect(), source);
        return true;
    }
}

class MythosOfVadrokRestrictionEffect extends RestrictionEffect {

    MythosOfVadrokRestrictionEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
    }

    MythosOfVadrokRestrictionEffect(final MythosOfVadrokRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfVadrokRestrictionEffect copy() {
        return new MythosOfVadrokRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
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
}
