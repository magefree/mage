package mage.cards.t;

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
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public final class DoomsTimePlatform extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland card from your graveyard");

    public DoomsTimePlatform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever you attack, exile target nonland card from your graveyard with two time counters on it.
        // If it doesn't have suspend, it gains suspend.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new DoomsTimePlatformEffect(), 1);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
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
        super(Outcome.Benefit);
        this.staticText = "exile target nonland card from your graveyard with two time counters on it. " +
                "If it doesn't have suspend, it gains suspend.";
    }

    private DoomsTimePlatformEffect(final DoomsTimePlatformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        SuspendAbility.addTimeCountersAndSuspend(card, 0, source, game);
        return true;
    }

    @Override
    public DoomsTimePlatformEffect copy() {
        return new DoomsTimePlatformEffect(this);
    }
}
