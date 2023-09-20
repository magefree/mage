package mage.cards.j;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class JovialEvil extends CardImpl {

    public JovialEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Jovial Evil deals X damage to target opponent, where X is twice the number of white creatures that player controls.
        this.getSpellAbility().addEffect(new JovialEvilEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private JovialEvil(final JovialEvil card) {
        super(card);
    }

    @Override
    public JovialEvil copy() {
        return new JovialEvil(this);
    }
}

class JovialEvilEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    JovialEvilEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target opponent, where X is twice the number of white creatures that player controls";
    }

    private JovialEvilEffect(final JovialEvilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            int amount = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, opponent.getId(), game)) {
                amount++;
            }
            if (amount > 0) {
                opponent.damage(amount * 2, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public JovialEvilEffect copy() {
        return new JovialEvilEffect(this);
    }
}
