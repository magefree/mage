package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class MaximumCarnage extends CardImpl {

    public MaximumCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters step and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Until your next turn, each creature attacks each combat if able and attacks a player other than you if able.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new AttacksIfAbleAllEffect(
                new FilterOpponentsCreaturePermanent("each creature an opponent controls"), Duration.UntilYourNextTurn
        ), new MaximumCarnageEffect());

        // II -- Add {R}{R}{R}.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new AddManaToManaPoolSourceControllerEffect(new Mana(ManaType.RED, 3)));

        // III -- This Saga deals 5 damage to each opponent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new DamagePlayersEffect(5, TargetController.OPPONENT));

        this.addAbility(sagaAbility);
    }

    private MaximumCarnage(final MaximumCarnage card) {
        super(card);
    }

    @Override
    public MaximumCarnage copy() {
        return new MaximumCarnage(this);
    }
}

class MaximumCarnageEffect extends RestrictionEffect {

    MaximumCarnageEffect() {
        super(Duration.UntilYourNextTurn);
        staticText = "and attacks a player other than you if able";
    }

    private MaximumCarnageEffect(final MaximumCarnageEffect effect) {
        super(effect);
    }

    @Override
    public MaximumCarnageEffect copy() {
        return new MaximumCarnageEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.getOpponents(permanent.getControllerId()).contains(source.getControllerId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null
                || game.getState().getPlayersInRange(attacker.getControllerId(), game).size() == 2) {  // just 2 players left, so it may attack you
            return true;
        }
        // A planeswalker controlled by the controller is the defender
        if (game.getPermanent(defenderId) != null) {
            return !game.getPermanent(defenderId).getControllerId().equals(source.getControllerId());
        }
        // The controller is the defender
        return !defenderId.equals(source.getControllerId());
    }
}
