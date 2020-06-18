package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfAll extends CardImpl {

    public SanctumOfAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, you may search your library and/or graveyard for a Shrine card and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SanctumOfAllSearchEffect(), TargetController.YOU, true));

        // If an ability of another Shrine you control triggers while you control six or more Shrines, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new SanctumOfAllTriggerEffect()));
    }

    private SanctumOfAll(final SanctumOfAll card) {
        super(card);
    }

    @Override
    public SanctumOfAll copy() {
        return new SanctumOfAll(this);
    }
}

class SanctumOfAllSearchEffect extends OneShotEffect {

    public static final FilterCard filter = new FilterCard("a Shrine card");

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    SanctumOfAllSearchEffect() {
        super(Outcome.Benefit);
        staticText = "search your library and/or graveyard for a Shrine card and put it onto the battlefield. If you search your library this way, shuffle it";
    }

    private SanctumOfAllSearchEffect(SanctumOfAllSearchEffect effect) {
        super(effect);
    }

    @Override
    public SanctumOfAllSearchEffect copy() {
        return new SanctumOfAllSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Optional<Card> maybeChosenCard = Optional.empty();
            boolean searchedLibrary = false;

            // from library
            Target target = new TargetCardInLibrary(0, 1, filter).withChooseHint("from library");
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && target.choose(Outcome.PutCardInPlay, controller.getId(), source.getSourceId(), game)) {
                maybeChosenCard = Optional.ofNullable(game.getCard(target.getFirstTarget()));
                searchedLibrary = true;
            }

            if (!maybeChosenCard.isPresent()) {
                // from graveyard
                target = new TargetCardInYourGraveyard(0, 1, filter, true).withChooseHint("from graveyard");
                if (target.canChoose(source.getSourceId(), controller.getId(), game)
                        && target.choose(Outcome.PutCardInPlay, controller.getId(), source.getSourceId(), game)) {
                    maybeChosenCard = Optional.ofNullable(game.getCard(target.getFirstTarget()));
                }
            }

            maybeChosenCard.ifPresent(card -> controller.moveCards(card, Zone.BATTLEFIELD, source, game));
            if (searchedLibrary) {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}

class SanctumOfAllTriggerEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    SanctumOfAllTriggerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an ability of another Shrine you control triggers while you control six or more Shrines, that ability triggers an additional time";
    }

    private SanctumOfAllTriggerEffect(SanctumOfAllTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SanctumOfAllTriggerEffect copy() {
        return new SanctumOfAllTriggerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Only triggers of the controller of Sanctum of All
        UUID playerId = event.getPlayerId();
        if (source.isControlledBy(playerId)) {
            // Only trigger while you control six or more Shrines
            int numShrines = game.getBattlefield().countAll(filter, playerId, game);
            if (numShrines >= 6) {
                // Only for triggers of Shrines
                Permanent permanent = game.getPermanent(event.getSourceId());
                return permanent != null && permanent.hasSubtype(SubType.SHRINE, game);
            }
        }
        return false;
    }
}