package mage.cards.c;

import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterPlayerOrPlaneswalker;
import mage.game.Game;
import mage.game.command.emblems.ChandraDressedToKillEmblem;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class ChandraDressedToKill extends CardImpl {

    private static final FilterPlayerOrPlaneswalker filter = new FilterPlayerOrPlaneswalker();

    public ChandraDressedToKill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(3);

        // +1: Add {R}. Chandra, Dressed to Kill deals 1 damage to up to one target player or planeswalker.
        Ability ability = new LoyaltyAbility(new AddManaToManaPoolSourceControllerEffect(new Mana(ManaType.RED, 1)), 1);
        ability.addEffect(new DamageTargetEffect(1));
        ability.addTarget(new TargetPlayerOrPlaneswalker(0, 1, filter, false));
        this.addAbility(ability);

        // +1: Exile the top card of your library. If it's red, you may cast it this turn.
        this.addAbility(new LoyaltyAbility(new ChandraDressedToKillExile1Effect(), 1));

        // −7: Exile the top five cards of your library. You may cast red spells from among them this turn.
        // You get an emblem with "Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell."
        ability = new LoyaltyAbility(new ChandraDressedToKillExile5Effect(), -7);
        ability.addEffect(new GetEmblemEffect(new ChandraDressedToKillEmblem()));
        this.addAbility(ability);
    }

    private ChandraDressedToKill(final ChandraDressedToKill card) {
        super(card);
    }

    @Override
    public ChandraDressedToKill copy() {
        return new ChandraDressedToKill(this);
    }
}

// +1 and -7 need to be different abilities.
// The +1 ability checks if the card is red as it resolves.  The -7 checks if it's red at the time you would cast it.
// Ex. Playing a Painter's Servant on red after the ability resolves.
// Ex. MDFCs like Mila, Crafty Companion.  Back half cannot be played from the +1 but it can from the -7.
class ChandraDressedToKillExile1Effect extends OneShotEffect {

    public ChandraDressedToKillExile1Effect() {
        super(Outcome.Benefit);
        staticText = "Exile the top card of your library. If it's red, you may cast it this turn";
    }

    private ChandraDressedToKillExile1Effect(final ChandraDressedToKillExile1Effect effect) {
        super(effect);
    }

    @Override
    public ChandraDressedToKillExile1Effect copy() {
        return new ChandraDressedToKillExile1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        MageObject sourceObject = source.getSourceObject(game);
        String exileName = sourceObject == null ? null : sourceObject.getIdName();
        controller.moveCardsToExile(card, source, game, true, exileId, exileName);
        if (game.getState().getZone(card.getId()) == Zone.EXILED && card.getColor(game).isRed()) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, false, true)
                    .setTargetPointer(new FixedTarget(card, game)), source);
        }
        return true;
    }
}

class ChandraDressedToKillExile5Effect extends OneShotEffect {

    public ChandraDressedToKillExile5Effect() {
        super(Outcome.Benefit);
        staticText = "Exile the top five cards of your library. You may cast red spells from among them this turn";
    }

    private ChandraDressedToKillExile5Effect(final ChandraDressedToKillExile5Effect effect) {
        super(effect);
    }

    @Override
    public ChandraDressedToKillExile5Effect copy() {
        return new ChandraDressedToKillExile5Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, 5);
        if (cards.isEmpty()) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        MageObject sourceObject = source.getSourceObject(game);
        String exileName = sourceObject == null ? null : sourceObject.getIdName();
        controller.moveCardsToExile(cards, source, game, true, exileId, exileName);
        cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
        if (!cards.isEmpty()) {
            game.addEffect(new ChandraDressedToKillPlayEffect()
                    .setTargetPointer(new FixedTargets(cards, game)), source);
        }
        return true;
    }
}

// Only used for the -7 ability (see comment at top)
class ChandraDressedToKillPlayEffect extends PlayFromNotOwnHandZoneTargetEffect {

    public ChandraDressedToKillPlayEffect() {
        super(Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, false, true);
    }

    private ChandraDressedToKillPlayEffect(final ChandraDressedToKillPlayEffect effect) {
        super(effect);
    }

    @Override
    public ChandraDressedToKillPlayEffect copy() {
        return new ChandraDressedToKillPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!super.applies(objectId, affectedAbility, source, game, playerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        return card != null && card.getColor(game).isRed();
    }
}
