package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrangAndShredder extends CardImpl {

    public KrangAndShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UTROM);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Whenever Krang & Shredder enter or attack, each opponent exiles cards from the top of their library until they exile a nonland card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new KrangAndShredderExileEffect())
                .setTriggerPhrase("Whenever {this} enter or attack, "));

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, you may cast a card exiled with Krang & Shredder without paying its mana cost.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new KrangAndShredderCastEffect(), false)
                .withInterveningIf(RevoltCondition.instance)
                .addHint(RevoltCondition.getHint())
                .setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());
    }

    private KrangAndShredder(final KrangAndShredder card) {
        super(card);
    }

    @Override
    public KrangAndShredder copy() {
        return new KrangAndShredder(this);
    }
}

class KrangAndShredderExileEffect extends OneShotEffect {

    KrangAndShredderExileEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles cards from the top of their library until they exile a nonland card";
    }

    private KrangAndShredderExileEffect(final KrangAndShredderExileEffect effect) {
        super(effect);
    }

    @Override
    public KrangAndShredderExileEffect copy() {
        return new KrangAndShredderExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            for (Card card : opponent.getLibrary().getCards(game)) {
                opponent.moveCardsToExile(
                        card, source, game, true,
                        CardUtil.getExileZoneId(game, source),
                        CardUtil.getSourceName(game, source)
                );
                if (!card.isLand(game)) {
                    break;
                }
            }
        }
        return true;
    }
}

class KrangAndShredderCastEffect extends OneShotEffect {

    KrangAndShredderCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a card exiled with {this} without paying its mana cost";
    }

    private KrangAndShredderCastEffect(final KrangAndShredderCastEffect effect) {
        super(effect);
    }

    @Override
    public KrangAndShredderCastEffect copy() {
        return new KrangAndShredderCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null
                && exileZone != null
                && !exileZone.isEmpty()
                && CardUtil.castSpellWithAttributesForFree(
                player, source, game, new CardsImpl(exileZone), StaticFilters.FILTER_CARD
        );
    }
}
