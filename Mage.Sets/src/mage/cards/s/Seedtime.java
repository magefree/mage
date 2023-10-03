package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Seedtime extends CardImpl {

    private static final String rule = "Cast this spell only during your turn.";
    private static final String rule2 = "Take an extra turn after this one if an opponent cast a blue spell this turn.";

    public Seedtime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Cast Seedtime only during your turn.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, MyTurnCondition.instance, rule)
                .addHint(MyTurnHint.instance));

        // Take an extra turn after this one if an opponent cast a blue spell this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new AddExtraTurnControllerEffect(), OpponentCastBlueSpellThisTurnCondition.instance, rule2));

    }

    private Seedtime(final Seedtime card) {
        super(card);
    }

    @Override
    public Seedtime copy() {
        return new Seedtime(this);
    }
}

enum OpponentCastBlueSpellThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (opponentId != null) {
                    List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                    if (spells != null) {
                        for (Spell spell : spells) {
                            if (spell != null
                                    && spell.getColor(game).isBlue()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
