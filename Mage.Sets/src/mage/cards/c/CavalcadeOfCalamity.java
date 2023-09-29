package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalcadeOfCalamity extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creaure you control with power 1 or less");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public CavalcadeOfCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever a creature you control with power 1 or less attacks, Cavalcade of Calamity deals 1 damage to the player or planeswalker that creature is attacking.
        this.addAbility(new AttacksAllTriggeredAbility(
                new CavalcadeOfCalamityEffect(), false, filter,
                SetTargetPointer.PERMANENT, false, false
        ));
    }

    private CavalcadeOfCalamity(final CavalcadeOfCalamity card) {
        super(card);
    }

    @Override
    public CavalcadeOfCalamity copy() {
        return new CavalcadeOfCalamity(this);
    }
}

class CavalcadeOfCalamityEffect extends OneShotEffect {

    CavalcadeOfCalamityEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to the player or planeswalker that creature is attacking.";
    }

    private CavalcadeOfCalamityEffect(final CavalcadeOfCalamityEffect effect) {
        super(effect);
    }

    @Override
    public CavalcadeOfCalamityEffect copy() {
        return new CavalcadeOfCalamityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference defenderMOR = game.getCombat().getDefenderMOR(targetPointer.getFirst(game, source));
        if (defenderMOR == null) {
            return false;
        }

        Permanent permanent = defenderMOR.getPermanent(game);
        if (permanent != null) {
            game.damagePlayerOrPermanent(
                    permanent.getId(), 1,
                    source.getSourceId(), source, game, false, true
            );
            return true;
        }

        Player player = game.getPlayer(defenderMOR.getSourceId());
        if (player != null) {
            game.damagePlayerOrPermanent(
                    player.getId(), 1,
                    source.getSourceId(), source, game, false, true
            );
            return true;
        }

        return false;
    }
}