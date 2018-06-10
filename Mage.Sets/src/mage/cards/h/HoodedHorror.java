
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HoodedHorror extends CardImpl {

    public HoodedHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hooded Horror can't be blocked as long as defending player controls the most creatures or is tied for the most.
      this.addAbility(new SimpleEvasionAbility(new HoodedHorrorCantBeBlockedEffect()));
    }

    public HoodedHorror(final HoodedHorror card) {
        super(card);
    }

    @Override
    public HoodedHorror copy() {
        return new HoodedHorror(this);
    }
}

class HoodedHorrorCantBeBlockedEffect extends RestrictionEffect {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HoodedHorrorCantBeBlockedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked as long as defending player controls the most creatures or is tied for the most";
    }

    public HoodedHorrorCantBeBlockedEffect(final HoodedHorrorCantBeBlockedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId()) && permanent.isAttacking()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxCreatures = 0;
            UUID playerIdWithMax = null;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int creatures = game.getBattlefield().countAll(filter, playerId, game);
                if (creatures > maxCreatures || (creatures == maxCreatures && playerId.equals(blocker.getControllerId())) ) {
                    maxCreatures = creatures;
                    playerIdWithMax = playerId;
                }
            }
            if (playerIdWithMax != null && playerIdWithMax.equals(blocker.getControllerId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HoodedHorrorCantBeBlockedEffect copy() {
        return new HoodedHorrorCantBeBlockedEffect(this);
    }
}
