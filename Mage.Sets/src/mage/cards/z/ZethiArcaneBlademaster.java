package mage.cards.z;

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
public final class ZethiArcaneBlademaster extends CardImpl {

    public ZethiArcaneBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {W/U}
        this.addAbility(new MultikickerAbility("{W/U}"));

        // When Chun-Li enters the battlefield, exile up to X target instant cards from your graveyard, where X is the number of times Chun-Li was kicked. Put a kick counter on each of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ZethiArcaneBlademasterExileEffect())
                .setTargetAdjuster(ZethiArcaneBlademasterAdjuster.instance));

        // Lightning Kick—Whenever Chun-Li attacks, copy each exiled card you own with a kick counter on it. You may cast the copies.
        this.addAbility(new AttacksTriggeredAbility(new ZethiArcaneBlademasterCastEffect()).withFlavorWord("Lightning Kick"));
    }

    private ZethiArcaneBlademaster(final ZethiArcaneBlademaster card) {
        super(card);
    }

    @Override
    public ZethiArcaneBlademaster copy() {
        return new ZethiArcaneBlademaster(this);
    }
}

enum ZethiArcaneBlademasterAdjuster implements TargetAdjuster {
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

class ZethiArcaneBlademasterExileEffect extends OneShotEffect {

    ZethiArcaneBlademasterExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to X target instant cards from your graveyard, "
                + "where X is the number of times {this} was kicked. Put a kick counter on each of them";
    }

    private ZethiArcaneBlademasterExileEffect(final ZethiArcaneBlademasterExileEffect effect) {
        super(effect);
    }

    @Override
    public ZethiArcaneBlademasterExileEffect copy() {
        return new ZethiArcaneBlademasterExileEffect(this);
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

class ZethiArcaneBlademasterCastEffect extends OneShotEffect {

    ZethiArcaneBlademasterCastEffect() {
        super(Outcome.Benefit);
        staticText = "copy each exiled card you own with a kick counter on it. You may cast the copies";
    }

    private ZethiArcaneBlademasterCastEffect(final ZethiArcaneBlademasterCastEffect effect) {
        super(effect);
    }

    @Override
    public ZethiArcaneBlademasterCastEffect copy() {
        return new ZethiArcaneBlademasterCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
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
        // simple way to choose the spells to cast; if you have a better tech, implement it!
        boolean keepGoing = true;
        Cards alreadyCast = new CardsImpl();
        while (keepGoing) {
            for (Card copiedCard : copies.getCards(game)) {
                if (alreadyCast.getCards(game).contains(copiedCard)
                        || !controller.chooseUse(outcome, "Cast the copied card? " + copiedCard.getIdName(), source, game)) {
                    continue;
                }
                alreadyCast.add(copiedCard);
                if (copiedCard.getSpellAbility() != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                    controller.cast(
                            controller.chooseAbilityForCast(copiedCard, game, false),
                            game, false, new ApprovingObject(source, game)
                    );
                    game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
                } else {
                    Logger.getLogger(ZethiArcaneBlademasterCastEffect.class).error("Chun Li, Countless Kicks: "
                            + "spell ability == null " + copiedCard.getName());
                }
            }
            // TODO: AI is one and done so improve this
            keepGoing = controller.chooseUse(Outcome.Detriment, "Do you wish to continue casting? ", source, game);
        }
        return true;
    }
}
