package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HoodedHorror extends CardImpl {

    public HoodedHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hooded Horror can't be blocked as long as defending player controls the most creatures or is tied for the most.
        this.addAbility(new SimpleEvasionAbility(new HoodedHorrorCantBeBlockedEffect()));
    }

    private HoodedHorror(final HoodedHorror card) {
        super(card);
    }

    @Override
    public HoodedHorror copy() {
        return new HoodedHorror(this);
    }
}

class HoodedHorrorCantBeBlockedEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HoodedHorrorCantBeBlockedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked as long as defending player controls the most creatures or is tied for the most";
    }

    private HoodedHorrorCantBeBlockedEffect(final HoodedHorrorCantBeBlockedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId()) && permanent.isAttacking();
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxCreatures = 0;
            UUID playerIdWithMax = null;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int creatures = game.getBattlefield().countAll(filter, playerId, game);
                if (creatures > maxCreatures || (creatures == maxCreatures && playerId.equals(blocker.getControllerId()))) {
                    maxCreatures = creatures;
                    playerIdWithMax = playerId;
                }
            }
            return playerIdWithMax == null || !playerIdWithMax.equals(blocker.getControllerId());
        }
        return true;
    }

    @Override
    public HoodedHorrorCantBeBlockedEffect copy() {
        return new HoodedHorrorCantBeBlockedEffect(this);
    }
}
