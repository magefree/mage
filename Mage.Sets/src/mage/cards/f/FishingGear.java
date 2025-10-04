package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FishingGear extends CardImpl {

    public FishingGear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, exile the top card of that player's library. If it's a permanent card, you may put it onto the battlefield under your control. If you don't, create a 1/1 blue Fish creature token.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new FishingGearEffect(), "equipped", false, true, true
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private FishingGear(final FishingGear card) {
        super(card);
    }

    @Override
    public FishingGear copy() {
        return new FishingGear(this);
    }
}

class FishingGearEffect extends OneShotEffect {

    FishingGearEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of that player's library. " +
                "If it's a permanent card, you may put it onto the battlefield under your control. " +
                "If you don't, create a 1/1 blue Fish creature token";
    }

    private FishingGearEffect(final FishingGearEffect effect) {
        super(effect);
    }

    @Override
    public FishingGearEffect copy() {
        return new FishingGearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        if (card.isPermanent(game) && controller.chooseUse(outcome, "Put it onto the battlefield?", source, game)) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        } else {
            new FishNoAbilityToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
