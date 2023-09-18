package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KardurDoomscourge extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("an attacking creature");

    public KardurDoomscourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Kardur, Doomscourge enters the battlefield, until your next turn, creatures your opponents control attack each combat if able and attack a player other than you if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttacksIfAbleAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, Duration.UntilYourNextTurn
        ).setText("until your next turn, creatures your opponents control attack each combat if able"));
        ability.addEffect(new KardurDoomscourgeEffect());
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);

        // Whenever an attacking creature dies, each opponent loses 1 life and you gain 1 life.
        ability = new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KardurDoomscourge(final KardurDoomscourge card) {
        super(card);
    }

    @Override
    public KardurDoomscourge copy() {
        return new KardurDoomscourge(this);
    }
}

class KardurDoomscourgeEffect extends RestrictionEffect {

    KardurDoomscourgeEffect() {
        super(Duration.UntilYourNextTurn);
        staticText = "and attack a player other than you if able";
    }

    private KardurDoomscourgeEffect(final KardurDoomscourgeEffect effect) {
        super(effect);
    }

    @Override
    public KardurDoomscourgeEffect copy() {
        return new KardurDoomscourgeEffect(this);
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
