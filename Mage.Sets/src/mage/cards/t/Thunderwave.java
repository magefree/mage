package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Thunderwave extends CardImpl {

    public Thunderwave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);

        // 1-9 | Thunderwave deals 3 damage to each creature.
        effect.addTableEntry(1, 9, new DamageAllEffect(3, StaticFilters.FILTER_PERMANENT_CREATURE));

        // 10-19 | You may choose a creature. Thunderwave deals 3 damage to each creature not chosen this way.
        effect.addTableEntry(10, 19, new ThunderwaveEffect());

        // 20 | Thunderwave deals 6 damage to each creature your opponents control.
        effect.addTableEntry(20, 20, new DamageAllEffect(6, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        this.getSpellAbility().addEffect(effect);
    }

    private Thunderwave(final Thunderwave card) {
        super(card);
    }

    @Override
    public Thunderwave copy() {
        return new Thunderwave(this);
    }
}

class ThunderwaveEffect extends OneShotEffect {

    ThunderwaveEffect() {
        super(Outcome.Benefit);
        staticText = "you may choose a creature. {this} deals 3 damage to each creature not chosen this way";
    }

    private ThunderwaveEffect(final ThunderwaveEffect effect) {
        super(effect);
    }

    @Override
    public ThunderwaveEffect copy() {
        return new ThunderwaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(0, 1);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            if (!permanent.getId().equals(target.getFirstTarget())) {
                permanent.damage(6, source, game);
            }
        }
        return true;
    }
}
