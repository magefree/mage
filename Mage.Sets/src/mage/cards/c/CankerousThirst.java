package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class CankerousThirst extends CardImpl {

    public CankerousThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B/G}");

        // If {B} was spent to cast Cankerous Thirst, you may have target creature get -3/-3 until end of turn. If {G} was spent to cast Cankerous Thirst, you may have target creature get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new CankerousThirstEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (1th effect -3/-3)")));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (2nd effect +3/+3)")));
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {B}{G} was spent.)</i>"));
    }

    private CankerousThirst(final CankerousThirst card) {
        super(card);
    }

    @Override
    public CankerousThirst copy() {
        return new CankerousThirst(this);
    }

}

class CankerousThirstEffect extends OneShotEffect {

    // Only used for getCondition
    private static final Condition condition = new CompoundCondition(
            new ManaWasSpentCondition(ColoredManaSymbol.B),
            new ManaWasSpentCondition(ColoredManaSymbol.G)
    );

    public CankerousThirstEffect() {
        super(Outcome.Benefit);
        this.staticText = "If {B} was spent to cast this spell, you may have target creature get -3/-3 until end of turn. If {G} was spent to cast this spell, you may have target creature get +3/+3 until end of turn";
    }

    public CankerousThirstEffect(final CankerousThirstEffect effect) {
        super(effect);
    }

    @Override
    public CankerousThirstEffect copy() {
        return new CankerousThirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (new ManaWasSpentCondition(ColoredManaSymbol.B).apply(game, source)) {
                Permanent targetCreature1 = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetCreature1 != null && controller.chooseUse(Outcome.UnboostCreature, "Let " + targetCreature1.getIdName() + " get -3/-3 until end of turn?", source, game)) {
                    ContinuousEffect effect = new BoostTargetEffect(-3, -3, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(targetCreature1, game));
                    game.addEffect(effect, source);
                }
            }
            if (new ManaWasSpentCondition(ColoredManaSymbol.G).apply(game, source)) {
                Permanent targetCreature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (targetCreature2 != null && controller.chooseUse(Outcome.UnboostCreature, "Let " + targetCreature2.getIdName() + " get +3/+3 until end of turn?", source, game)) {
                    ContinuousEffect effect = new BoostTargetEffect(+3, +3, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(targetCreature2, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
