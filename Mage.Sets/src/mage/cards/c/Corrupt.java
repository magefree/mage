package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Corrupt extends CardImpl {

    public Corrupt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");

        // Corrupt deals damage to any target equal to the number of Swamps you control. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CorruptEffect());
    }

    private Corrupt(final Corrupt card) {
        super(card);
    }

    @Override
    public Corrupt copy() {
        return new Corrupt(this);
    }

}

class CorruptEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamps");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CorruptEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to any target equal to the number of Swamps you control. You gain life equal to the damage dealt this way";
    }

    public CorruptEffect(final CorruptEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (amount > 0) {
            int damageDealt = amount;
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                damageDealt = permanent.damage(amount, source.getSourceId(), source, game, false, true);
            } else {
                Player player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    damageDealt = player.damage(amount, source.getSourceId(), source, game);
                } else
                    return false;
            }
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                you.gainLife(damageDealt, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public CorruptEffect copy() {
        return new CorruptEffect(this);
    }

}