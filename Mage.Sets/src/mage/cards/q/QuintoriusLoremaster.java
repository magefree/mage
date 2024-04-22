package mage.cards.q;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.GameState;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spirit32Token;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuintoriusLoremaster extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("noncreature, nonland card from your graveyard");
    private static final FilterCard filter2 = new FilterCard("card exiled with this permanent");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent(SubType.SPIRIT, "a Spirit");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(QuintoriusLoremasterPredicate.instance);
    }

    public QuintoriusLoremaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, exile target noncreature, nonland card from your graveyard. Create a 3/2 red and white Spirit creature token.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ExileTargetForSourceEffect(), TargetController.YOU, false
        );
        ability.addEffect(new CreateTokenEffect(new Spirit32Token()));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // {1}{R}{W}, {T}, Sacrifice a Spirit: Choose target card exiled with Quintorius. You may cast that card this turn without paying its mana cost. If that spell would be put into a graveyard, put it on the bottom of its owner's library instead.
        ability = new SimpleActivatedAbility(new QuintoriusLoremasterEffect(), new ManaCostsImpl<>("{1}{R}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter3));
        ability.addTarget(new TargetCardInExile(filter2));
        this.addAbility(ability);
    }

    private QuintoriusLoremaster(final QuintoriusLoremaster card) {
        super(card);
    }

    @Override
    public QuintoriusLoremaster copy() {
        return new QuintoriusLoremaster(this);
    }
}

enum QuintoriusLoremasterPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .of(game)
                .map(Game::getState)
                .map(GameState::getExile)
                .map(exile -> {
                    Ability source = input.getSource();
                    Permanent quintorius = source.getSourcePermanentOrLKI(game);
                    if(quintorius == null) {
                        return null;
                    }
                    UUID exileZoneId = CardUtil.getExileZoneId(game, quintorius.getId(), quintorius.getZoneChangeCounter(game));
                    return exile.getExileZone(exileZoneId);
                })
                .filter(Objects::nonNull)
                .map(exile -> exile.contains(input.getObject().getId()))
                .orElse(false);
    }
}

class QuintoriusLoremasterEffect extends OneShotEffect {

    QuintoriusLoremasterEffect() {
        super(Outcome.Benefit);
        staticText = "choose target card exiled with {this}. You may cast that card this turn " +
                "without paying its mana cost. If that spell would be put into a graveyard, " +
                "put it on the bottom of its owner's library instead";
    }

    private QuintoriusLoremasterEffect(final QuintoriusLoremasterEffect effect) {
        super(effect);
    }

    @Override
    public QuintoriusLoremasterEffect copy() {
        return new QuintoriusLoremasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true
        ).setTargetPointer(new FixedTarget(card, game)), source);
        game.addEffect(new QuintoriusLoremasterReplacementEffect(card, game), source);
        return true;
    }
}

class QuintoriusLoremasterReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    QuintoriusLoremasterReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card, game, 1);
    }

    private QuintoriusLoremasterReplacementEffect(final QuintoriusLoremasterReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public QuintoriusLoremasterReplacementEffect copy() {
        return new QuintoriusLoremasterReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return controller != null
                && card != null
                && controller.putCardsOnBottomOfLibrary(card, game, source, false);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(mor.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && mor.zoneCounterIsCurrent(game);
    }
}