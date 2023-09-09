package mage.cards.e;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class EnergyTap extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public EnergyTap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Tap target untapped creature you control. If you do, add an amount of {C} equal to that creature's converted mana cost.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new EnergyTapEffect());
    }

    private EnergyTap(final EnergyTap card) {
        super(card);
    }

    @Override
    public EnergyTap copy() {
        return new EnergyTap(this);
    }
}

class EnergyTapEffect extends OneShotEffect {

    EnergyTapEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Tap target untapped creature you control. If you do, add an amount of {C} equal to that creature's mana value";
    }

    private EnergyTapEffect(final EnergyTapEffect effect) {
        super(effect);
    }

    @Override
    public EnergyTapEffect copy() {
        return new EnergyTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean applied = false;
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            applied = targetCreature.tap(source, game);
            if (applied) {
                player.getManaPool().addMana(new Mana(0, 0, 0, 0, 0, 0, 0, targetCreature.getManaValue()), game, source);
            }
        }
        return applied;
    }
}
