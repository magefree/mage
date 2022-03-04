package mage.cards.c;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChunLiCountlessKicks extends CardImpl {

    public ChunLiCountlessKicks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {W/U}
        this.addAbility(new MultikickerAbility("{W/U}"));

        // When Chun-Li enters the battlefield, exile up to X target instant cards from your graveyard, where X is the number of times Chun-Li was kicked. Put a kick counter on each of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChunLiCountlessKicksExileEffect())
                .setTargetAdjuster(ChunLiCountlessKicksAdjuster.instance));

        // Lightning Kick—Whenever Chun-Li attacks, copy each exiled card you own with a kick counter on it. You may cast the copies.
        this.addAbility(new AttacksTriggeredAbility(new ChunLiCountlessKicksCastEffect()).withFlavorWord("Lightning Kick"));
    }

    private ChunLiCountlessKicks(final ChunLiCountlessKicks card) {
        super(card);
    }

    @Override
    public ChunLiCountlessKicks copy() {
        return new ChunLiCountlessKicks(this);
    }
}

enum ChunLiCountlessKicksAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCard filter = new FilterCard("instant cards from your graveyard");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int count = MultikickerCount.instance.calculate(game, ability, null);
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(0, count, filter));
    }
}

class ChunLiCountlessKicksExileEffect extends OneShotEffect {

    ChunLiCountlessKicksExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to X target instant cards from your graveyard, " +
                "where X is the number of times {this} was kicked. Put a kick counter on each of them";
    }

    private ChunLiCountlessKicksExileEffect(final ChunLiCountlessKicksExileEffect effect) {
        super(effect);
    }

    @Override
    public ChunLiCountlessKicksExileEffect copy() {
        return new ChunLiCountlessKicksExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.getCards(game).forEach(card -> card.addCounters(CounterType.KICK.createInstance(), source, game));
        return true;
    }
}

class ChunLiCountlessKicksCastEffect extends OneShotEffect {

    ChunLiCountlessKicksCastEffect() {
        super(Outcome.Benefit);
        staticText = "copy each exiled card you own with a kick counter on it. You may cast the copies";
    }

    private ChunLiCountlessKicksCastEffect(final ChunLiCountlessKicksCastEffect effect) {
        super(effect);
    }

    @Override
    public ChunLiCountlessKicksCastEffect copy() {
        return new ChunLiCountlessKicksCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getExile().getAllCards(game, source.getControllerId()));
        cards.removeIf(uuid -> !game.getCard(uuid).getCounters(game).containsKey(CounterType.KICK));
        if (cards.isEmpty()) {
            return false;
        }
        Cards copies = new CardsImpl();
        for (Card card : cards.getCards(game)) {
            Card copiedCard = game.copyCard(card, source, source.getControllerId());
            game.getExile().add(source.getSourceId(), "", copiedCard);
            game.getState().setZone(copiedCard.getId(), Zone.EXILED);
            copies.add(copiedCard);
        }
        for (Card copiedCard : copies.getCards(game)) {
            if (!player.chooseUse(outcome, "Cast the copied card?", source, game)) {
                continue;
            }
            if (copiedCard.getSpellAbility() != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                player.cast(
                        player.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            } else {
                Logger.getLogger(ChunLiCountlessKicksCastEffect.class).error("Chun Li, Countless Kicks: "
                        + "spell ability == null " + copiedCard.getName());
            }
        }
        return true;
    }
}
