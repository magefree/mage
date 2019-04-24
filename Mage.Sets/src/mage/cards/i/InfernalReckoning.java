package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class InfernalReckoning extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("colorless creature");

    static {
        filter.add(new ColorlessPredicate());
    }

    public InfernalReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile target colorless creature. You gain life equal to its power.
        this.getSpellAbility().addEffect(new InfernalJudgmentEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public InfernalReckoning(final InfernalReckoning card) {
        super(card);
    }

    @Override
    public InfernalReckoning copy() {
        return new InfernalReckoning(this);
    }
}

class InfernalJudgmentEffect extends OneShotEffect {

    public InfernalJudgmentEffect() {
        super(Outcome.GainLife);
        staticText = "exile target colorless creature. You gain life equal to its power";
    }

    public InfernalJudgmentEffect(final InfernalJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public InfernalJudgmentEffect copy() {
        return new InfernalJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        int creaturePower = permanent.getPower().getValue();
        permanent.moveToExile(null, null, source.getSourceId(), game);
        game.applyEffects();
        player.gainLife(creaturePower, game, source);
        return true;
    }
}
