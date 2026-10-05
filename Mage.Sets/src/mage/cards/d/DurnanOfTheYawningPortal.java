package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author brahle
 */
public final class DurnanOfTheYawningPortal extends CardImpl {

    public DurnanOfTheYawningPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Durnan attacks, look at the top four cards of your library. You may exile a creature card from among them. Put the rest on the bottom of your library in any order. For as long as that card remains exiled, you may cast it. That spell has undaunted.
        this.addAbility(new AttacksTriggeredAbility(new DurnanOfTheYawningPortalEffect()));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private DurnanOfTheYawningPortal(final DurnanOfTheYawningPortal card) {
        super(card);
    }

    @Override
    public DurnanOfTheYawningPortal copy() {
        return new DurnanOfTheYawningPortal(this);
    }
}

class DurnanOfTheYawningPortalEffect extends OneShotEffect {

    DurnanOfTheYawningPortalEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top four cards of your library. You may exile a creature card from among them. "
                + "Put the rest on the bottom of your library in any order. For as long as that card remains exiled, "
                + "you may cast it. That spell has undaunted. <i>(It costs {1} less to cast for each opponent.)</i>";
    }

    private DurnanOfTheYawningPortalEffect(final DurnanOfTheYawningPortalEffect effect) {
        super(effect);
    }

    @Override
    public DurnanOfTheYawningPortalEffect copy() {
        return new DurnanOfTheYawningPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(Outcome.Exile, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            cards.remove(card);
            player.moveCards(card, Zone.EXILED, source, game);
            CardUtil.makeCardPlayable(game, source, card, true, Duration.Custom, false);
            game.addEffect(new DurnanUndauntedEffect(new MageObjectReference(card.getMainCard(), game)), source);
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}

class DurnanUndauntedEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;
    private final Ability ability = new UndauntedAbility();

    DurnanUndauntedEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
    }

    private DurnanUndauntedEffect(final DurnanUndauntedEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public DurnanUndauntedEffect copy() {
        return new DurnanUndauntedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(mor.getSourceId());
        if (card == null) {
            discard();
            return false;
        }
        int zcc = card.getZoneChangeCounter(game);
        if (zcc == mor.getZoneChangeCounter() && game.getState().getZone(card.getId()) == Zone.EXILED) {
            for (MageObject part : CardUtil.getObjectPartsAsObjects(card)) {
                if (part instanceof Card) {
                    game.getState().addOtherAbility((Card) part, ability);
                }
            }
            return true;
        }
        if (zcc == mor.getZoneChangeCounter() + 1 && (game.getState().getZone(card.getId()) == Zone.STACK
                || game.getState().getStack().stream().anyMatch(s -> s.getSourceId().equals(card.getId())
                || (s instanceof Spell && ((Spell) s).getCard().getMainCard().getId().equals(card.getId()))))) {
            for (MageObject part : CardUtil.getObjectPartsAsObjects(card)) {
                if (part instanceof Card) {
                    game.getState().addOtherAbility((Card) part, ability);
                }
            }
            return true;
        }
        discard();
        return false;
    }
}
