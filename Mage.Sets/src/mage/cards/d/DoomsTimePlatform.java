package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public final class DoomsTimePlatform extends CardImpl {

    public DoomsTimePlatform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever you attack, exile target nonland card from your graveyard with two time counters on it. If it doesn't have suspend, it gains suspend.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new DoomsTimePlatformEffect(), 1);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_NON_LAND));
        this.addAbility(ability);
    }

    private DoomsTimePlatform(final DoomsTimePlatform card) {
        super(card);
    }

    @Override
    public DoomsTimePlatform copy() {
        return new DoomsTimePlatform(this);
    }
}

class DoomsTimePlatformEffect extends OneShotEffect {

    DoomsTimePlatformEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland card from your graveyard with two time counters on it. "
                + "If it doesn't have suspend, it gains suspend";
    }

    private DoomsTimePlatformEffect(final DoomsTimePlatformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (card == null || controller == null || !controller.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        return SuspendAbility.addTimeCountersAndSuspend(card, 2, source, game);
    }

    @Override
    public DoomsTimePlatformEffect copy() {
        return new DoomsTimePlatformEffect(this);
    }
}
