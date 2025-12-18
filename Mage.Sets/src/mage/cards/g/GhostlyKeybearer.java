package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostlyKeybearer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM);

    public GhostlyKeybearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature deals combat damage to a player, unlock a locked door of up to one target Room you control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new GhostlyKeybearerEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private GhostlyKeybearer(final GhostlyKeybearer card) {
        super(card);
    }

    @Override
    public GhostlyKeybearer copy() {
        return new GhostlyKeybearer(this);
    }
}

class GhostlyKeybearerEffect extends OneShotEffect {

    GhostlyKeybearerEffect() {
        super(Outcome.Benefit);
        staticText = "unlock a locked door of up to one target Room you control";
    }

    private GhostlyKeybearerEffect(final GhostlyKeybearerEffect effect) {
        super(effect);
    }

    @Override
    public GhostlyKeybearerEffect copy() {
        return new GhostlyKeybearerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || !permanent.isLeftDoorUnlocked() && !permanent.isRightDoorUnlocked()) {
            return false;
        }
        boolean unlockLeft;
        if (!permanent.isLeftDoorUnlocked() && permanent.isRightDoorUnlocked()) {
            unlockLeft = true;
        } else if (permanent.isLeftDoorUnlocked() && !permanent.isRightDoorUnlocked()) {
            unlockLeft = false;
        } else {
            unlockLeft = player.chooseUse(
                    Outcome.Neutral, "Unlock the left door or the right door?",
                    null, "Left", "Right", source, game
            );
        }
        return permanent.unlockDoor(game, source, unlockLeft);
    }
}
