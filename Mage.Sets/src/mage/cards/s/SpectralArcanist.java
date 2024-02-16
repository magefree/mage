package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectralArcanist extends CardImpl {

    public SpectralArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spectral Arcanist enters the battlefield, you may cast an instant or sorcery spell with mana value less than or equal to the number of Spirits you control from a graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpectralArcanistCastEffect())
                .addHint(SpectralArcanistCastEffect.getHint()));
    }

    private SpectralArcanist(final SpectralArcanist card) {
        super(card);
    }

    @Override
    public SpectralArcanist copy() {
        return new SpectralArcanist(this);
    }
}

class SpectralArcanistCastEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);
    private static final Hint hint = new ValueHint("Spirits you control", new PermanentsOnBattlefieldCount(filter));

    SpectralArcanistCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast an instant or sorcery spell with mana value less than or equal to " +
                "the number of Spirits you control from a graveyard without paying its mana cost. " +
                "If that spell would be put into a graveyard, exile it instead";
    }

    private SpectralArcanistCastEffect(final SpectralArcanistCastEffect effect) {
        super(effect);
    }

    @Override
    public SpectralArcanistCastEffect copy() {
        return new SpectralArcanistCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCard filterCard = new FilterInstantOrSorceryCard();
        filterCard.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN,
                game.getBattlefield().count(filter, source.getControllerId(), source, game)
        ));
        Cards cards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .forEach(cards::addAll);
        return CardUtil.castSpellWithAttributesForFree(
                player, source, game, cards, filterCard,
                SpectralArcanistTracker.instance
        );
    }

    public static Hint getHint() {
        return hint;
    }
}

enum SpectralArcanistTracker implements CardUtil.SpellCastTracker {
    instance;

    @Override
    public boolean checkCard(Card card, Game game) {
        return true;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        game.addEffect(new SpectralArcanistReplacementEffect(card, game), source);
    }
}

class SpectralArcanistReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    SpectralArcanistReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card.getMainCard(), game);
    }

    private SpectralArcanistReplacementEffect(final SpectralArcanistReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SpectralArcanistReplacementEffect copy() {
        return new SpectralArcanistReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return controller != null
                && card != null
                && controller.moveCards(card, Zone.EXILED, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(mor.getSourceId());
    }
}
