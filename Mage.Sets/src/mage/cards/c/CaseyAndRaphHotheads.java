package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class CaseyAndRaphHotheads extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterPlayer();
    private static final FilterPlayer filter2 = new FilterPlayer();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
    }

    public CaseyAndRaphHotheads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Casey & Raph enter, choose one or both. Each mode must target a different player.
        // * Target player exiles the top card of their library. Until that player's next end step, they may play that card without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CaseyAndRaphHotheadsEffect());
        ability.addTarget(new TargetPlayer(filter1).setTargetTag(1).withChooseHint("to exile the top card of their library"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        ability.getModes().setLimitUsageByOnce(false);
        ability.getModes().setMaxModesFilter(filter0);

        // * Target player creates two Treasure tokens.
        ability.addMode(new Mode(new CreateTokenTargetEffect(new TreasureToken(), 2))
                .addTarget(new TargetPlayer(filter2).setTargetTag(2).withChooseHint("to create two Treasure tokens")));
        this.addAbility(ability);
    }

    private CaseyAndRaphHotheads(final CaseyAndRaphHotheads card) {
        super(card);
    }

    @Override
    public CaseyAndRaphHotheads copy() {
        return new CaseyAndRaphHotheads(this);
    }
}

class CaseyAndRaphHotheadsEffect extends OneShotEffect {

    CaseyAndRaphHotheadsEffect() {
        super(Outcome.PlayForFree);
        staticText = "target player exiles the top card of their library. Until that player's next end step, "
                + "they may play that card without paying its mana cost";
    }

    private CaseyAndRaphHotheadsEffect(final CaseyAndRaphHotheadsEffect effect) {
        super(effect);
    }

    @Override
    public CaseyAndRaphHotheadsEffect copy() {
        return new CaseyAndRaphHotheadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        game.addEffect(new CaseyAndRaphHotheadsPlayEffect(player.getId())
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class CaseyAndRaphHotheadsPlayEffect extends PlayFromNotOwnHandZoneTargetEffect {

    private final UUID playerId;

    CaseyAndRaphHotheadsPlayEffect(UUID playerId) {
        super(Zone.EXILED, TargetController.ANY, Duration.UntilYourNextEndStep, true);
        this.playerId = playerId;
    }

    private CaseyAndRaphHotheadsPlayEffect(final CaseyAndRaphHotheadsPlayEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
    }

    @Override
    public CaseyAndRaphHotheadsPlayEffect copy() {
        return new CaseyAndRaphHotheadsPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        // until that player's next end step, not the controller's
        setStartingControllerAndTurnNum(game, playerId, activePlayerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return this.playerId.equals(playerId)
                && super.applies(objectId, affectedAbility, source, game, playerId);
    }
}
