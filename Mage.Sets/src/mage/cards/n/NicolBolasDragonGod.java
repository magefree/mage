package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public final class NicolBolasDragonGod extends CardImpl {

    public NicolBolasDragonGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{U}{B}{B}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // Nicol Bolas, Dragon-God has all loyalty abilities of all other planeswalkers on the battlefield.
        this.addAbility(new SimpleStaticAbility(new NicolBolasDragonGodGainAbilitiesEffect()));

        // +1: You draw a card. Each opponent exiles a card from their hand or a permanent they control.
        this.addAbility(new LoyaltyAbility(new NicolBolasDragonGodPlusOneEffect(), 1));

        // -3: Destroy target creature or planeswalker.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // -8: Each opponent who doesn't control a legendary creature or planeswalker loses the game.
        this.addAbility(new LoyaltyAbility(new NicolBolasDragonGodMinus8Effect(), -8));
    }

    private NicolBolasDragonGod(final NicolBolasDragonGod card) {
        super(card);
    }

    @Override
    public NicolBolasDragonGod copy() {
        return new NicolBolasDragonGod(this);
    }
}

class NicolBolasDragonGodGainAbilitiesEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    NicolBolasDragonGodGainAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all loyalty abilities of all other planeswalkers on the battlefield.";
    }

    private NicolBolasDragonGodGainAbilitiesEffect(final NicolBolasDragonGodGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null) {
            return true;
        }
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof LoyaltyAbility) {
                    perm.addAbility(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

    @Override
    public NicolBolasDragonGodGainAbilitiesEffect copy() {
        return new NicolBolasDragonGodGainAbilitiesEffect(this);
    }
}

class NicolBolasDragonGodPlusOneEffect extends OneShotEffect {

    NicolBolasDragonGodPlusOneEffect() {
        super(Outcome.Benefit);
        staticText = "You draw a card. Each opponent exiles a card from their "
                + "hand or a permanent they control.";
    }

    private NicolBolasDragonGodPlusOneEffect(final NicolBolasDragonGodPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasDragonGodPlusOneEffect copy() {
        return new NicolBolasDragonGodPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean applied = false;
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, game);
        Set<Card> cardsOnBattlefield = new LinkedHashSet<>();
        Set<Card> cards = new LinkedHashSet<>();
        for (UUID opponentId : game.getState().getPlayersInRange(player.getId(), game)) {
            if (!player.hasOpponent(opponentId, game)) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            if (opponent.getHand().isEmpty()
                    || !opponent.chooseUse(outcome, "Exile a card in your hand or a permanent you control?",
                            null, "Card in hand", "Permanent", source, game)) {
                TargetPermanent target = new TargetControlledPermanent();
                target.setNotTarget(true);
                target.setTargetController(opponentId);
                if (opponent.choose(outcome, target, source.getSourceId(), game)) {
                    MageObject mageObject = game.getObject(target.getFirstTarget());
                    if (mageObject != null
                            && mageObject instanceof Permanent) {
                        cardsOnBattlefield.add((Card) mageObject);
                    }
                }
            } else {
                TargetCardInHand target = new TargetCardInHand();
                if (opponent.choose(outcome, opponent.getHand(), target, game)
                        && game.getCard(target.getFirstTarget()) != null) {
                    cards.add(game.getCard(target.getFirstTarget()));
                }
            }
        }
        cards.addAll(cardsOnBattlefield);
        for (Card card : cards) {
            if (card != null) {
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null
                        && owner.moveCards(card, Zone.EXILED, source, game)) {
                    applied = true;
                }
            }
        }
        return applied;
    }
}

class NicolBolasDragonGodMinus8Effect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    NicolBolasDragonGodMinus8Effect() {
        super(Outcome.Benefit);
        staticText = "Each opponent who doesn't control a legendary creature or planeswalker loses the game.";
    }

    private NicolBolasDragonGodMinus8Effect(final NicolBolasDragonGodMinus8Effect effect) {
        super(effect);
    }

    @Override
    public NicolBolasDragonGodMinus8Effect copy() {
        return new NicolBolasDragonGodMinus8Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.hasOpponent(source.getControllerId(), game)) {
                continue;
            }
            if (game.getBattlefield().getAllActivePermanents(filter, opponentId, game).isEmpty()) {
                opponent.lost(game);
            }
        }
        return true;
    }
}
