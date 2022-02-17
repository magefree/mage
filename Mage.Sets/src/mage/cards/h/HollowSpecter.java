package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HollowSpecter extends CardImpl {

    public HollowSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hollow Specter deals combat damage to a player, you may pay {X}.
        // If you do, that player reveals X cards from their hand and you choose one of them. That player discards that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HollowSpecterEffect(), false, true));
    }

    private HollowSpecter(final HollowSpecter card) {
        super(card);
    }

    @Override
    public HollowSpecter copy() {
        return new HollowSpecter(this);
    }
}

class HollowSpecterEffect extends OneShotEffect {

    public HollowSpecterEffect() {
        super(Outcome.Discard);
        staticText = "you may pay {X}. If you do, that player reveals X cards from their hand and you choose one of them. That player discards that card";
    }

    public HollowSpecterEffect(final HollowSpecterEffect effect) {
        super(effect);
    }

    @Override
    public HollowSpecterEffect copy() {
        return new HollowSpecterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null && controller.chooseUse(Outcome.Benefit, "Pay {X}?", source, game)) {
            int payCount = ManaUtil.playerPaysXGenericMana(true, "Hollow Specter", controller, source, game);
            if (payCount > 0) {
                return new DiscardCardYouChooseTargetEffect(TargetController.ANY, payCount).setTargetPointer(targetPointer).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
