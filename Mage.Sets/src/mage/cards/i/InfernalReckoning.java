package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernalReckoning extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("colorless creature");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public InfernalReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile target colorless creature. You gain life equal to its power.
        this.getSpellAbility().addEffect(new InfernalJudgmentEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private InfernalReckoning(final InfernalReckoning card) {
        super(card);
    }

    @Override
    public InfernalReckoning copy() {
        return new InfernalReckoning(this);
    }
}

class InfernalJudgmentEffect extends OneShotEffect {

    InfernalJudgmentEffect() {
        super(Outcome.GainLife);
        staticText = "exile target colorless creature. You gain life equal to its power";
    }

    private InfernalJudgmentEffect(final InfernalJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public InfernalJudgmentEffect copy() {
        return new InfernalJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        int creaturePower = permanent.getPower().getValue();
        player.moveCards(permanent, Zone.EXILED, source, game);
        player.gainLife(creaturePower, game, source);
        return true;
    }
}
