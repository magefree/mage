package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTargets;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GilgameshMasterAtArms extends CardImpl {

    public GilgameshMasterAtArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Gilgamesh enters or attacks, look at the top six cards of your library. You may put any number of Equipment cards from among them onto the battlefield. Put the rest on the bottom of your library in a random order. When you put one or more Equipment onto the battlefield this way, you may attach one of them to a Samurai you control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GilgameshMasterAtArmsLookEffect()));
    }

    private GilgameshMasterAtArms(final GilgameshMasterAtArms card) {
        super(card);
    }

    @Override
    public GilgameshMasterAtArms copy() {
        return new GilgameshMasterAtArms(this);
    }
}

class GilgameshMasterAtArmsLookEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Equipment cards");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    GilgameshMasterAtArmsLookEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may put any number of Equipment cards " +
                "from among them onto the battlefield. Put the rest on the bottom of your library in a random order. " +
                "When you put one or more Equipment onto the battlefield this way, you may attach one of them to a Samurai you control";
    }

    private GilgameshMasterAtArmsLookEffect(final GilgameshMasterAtArmsLookEffect effect) {
        super(effect);
    }

    @Override
    public GilgameshMasterAtArmsLookEffect copy() {
        return new GilgameshMasterAtArmsLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        player.choose(outcome, cards, target, source, game);
        Cards toPlay = new CardsImpl(target.getTargets());
        player.moveCards(toPlay, Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        Set<Permanent> permanents = toPlay
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (!permanents.isEmpty()) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new GilgameshMasterAtArmsAttachEffect(permanents, game), false
            ), source);
        }
        return true;
    }
}

class GilgameshMasterAtArmsAttachEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAMURAI);

    GilgameshMasterAtArmsAttachEffect(Collection<Permanent> permanents, Game game) {
        super(Outcome.Benefit);
        staticText = "you may attach one of them to a Samurai you control";
        this.setTargetPointer(new FixedTargets(permanents, game));
    }

    private GilgameshMasterAtArmsAttachEffect(final GilgameshMasterAtArmsAttachEffect effect) {
        super(effect);
    }

    @Override
    public GilgameshMasterAtArmsAttachEffect copy() {
        return new GilgameshMasterAtArmsAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (player == null || permanents.isEmpty()) {
            return false;
        }
        Permanent samurai = Optional
                .of(new TargetPermanent(0, 1, filter, true))
                .map(t -> {
                    player.choose(outcome, t, source, game);
                    return t;
                })
                .map(TargetImpl::getFirstTarget)
                .map(game::getPermanent)
                .orElse(null);
        if (samurai == null) {
            return false;
        }
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                return samurai.addAttachment(RandomUtil.randomFromCollection(permanents).getId(), source, game);
            default:
                break;
        }
        FilterPermanent filterPermanent = new FilterPermanent("Equipment");
        filterPermanent.add(new PermanentReferenceInCollectionPredicate(permanents, game));
        return Optional
                .of(new TargetPermanent(0, 1, filterPermanent, true))
                .map(t -> {
                    player.choose(outcome, t, source, game);
                    return t;
                })
                .map(TargetImpl::getFirstTarget)
                .map(game::getPermanent)
                .map(equipment -> samurai.addAttachment(equipment.getId(), source, game))
                .orElse(false);
    }
}
