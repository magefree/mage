package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Taunt extends CardImpl {

    public Taunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // During target player's next turn, creatures that player controls attack you if able.
        this.getSpellAbility().addEffect(new TauntEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Taunt(final Taunt card) {
        super(card);
    }

    @Override
    public Taunt copy() {
        return new Taunt(this);
    }
}

class TauntEffect extends RequirementEffect {

    TauntEffect() {
        super(Duration.Custom);
        staticText = "During target player's next turn, creatures that player controls attack you if able";
    }

    TauntEffect(final TauntEffect effect) {
        super(effect);
    }

    @Override
    public TauntEffect copy() {
        return new TauntEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game);
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getControllerId();
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
