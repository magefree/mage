package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DauthiVoidwalker extends CardImpl {

    public DauthiVoidwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // If a card would be put into an opponent's graveyard from anywhere, instead exile it with a void counter on it.
        this.addAbility(new SimpleStaticAbility(new DauthiVoidwalkerReplacementEffect()));

        // {T}, Sacrifice Dauthi Voidwalker: Choose an exiled card an opponent owns with a void counter on it. You may play it this turn without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(new DauthiVoidwalkerPlayEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DauthiVoidwalker(final DauthiVoidwalker card) {
        super(card);
    }

    @Override
    public DauthiVoidwalker copy() {
        return new DauthiVoidwalker(this);
    }
}

class DauthiVoidwalkerReplacementEffect extends ReplacementEffectImpl {

    DauthiVoidwalkerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if a card would be put into an opponent's graveyard from anywhere, "
                + "instead exile it with a void counter on it";
    }

    private DauthiVoidwalkerReplacementEffect(final DauthiVoidwalkerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DauthiVoidwalkerReplacementEffect copy() {
        return new DauthiVoidwalkerReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = ((ZoneChangeEvent) event).getTarget();
        if (card == null) {
            card = game.getCard(event.getTargetId());
        }

        if (controller == null || card == null) {
            return false;
        }
        CardUtil.moveCardWithCounter(game, source, controller, card, Zone.EXILED, CounterType.VOID.createInstance());
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && !(((ZoneChangeEvent) event).getTarget() instanceof PermanentToken)
                && game.getOpponents(source.getControllerId()).contains(game.getOwnerId(event.getTargetId()));
    }
}

class DauthiVoidwalkerPlayEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCard("exiled card an opponent owns with a void counter on it");

    static {
        filter.add(CounterType.VOID.getPredicate());
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    DauthiVoidwalkerPlayEffect() {
        super(Outcome.Benefit);
        staticText = "choose an exiled card an opponent owns with a void counter on it. "
                + "You may play it this turn without paying its mana cost";
    }

    private DauthiVoidwalkerPlayEffect(final DauthiVoidwalkerPlayEffect effect) {
        super(effect);
    }

    @Override
    public DauthiVoidwalkerPlayEffect copy() {
        return new DauthiVoidwalkerPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInExile target = new TargetCardInExile(
                0, 1, filter, null, true
        );
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true, false
        ).setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
