package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class InvertInvent extends SplitCard {

    public InvertInvent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/R}", "{4}{U}{R}", SpellAbilityType.SPLIT);

        // Invert
        // Switch the power and toughness of each of up to two target creatures until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new InvertEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Invent
        // Search your library for an instant card and/or a sorcery card, reveal them, put them into your hand, then shuffle your library.
        this.getRightHalfCard().getSpellAbility().addEffect(new InventEffect());
    }

    public InvertInvent(final InvertInvent card) {
        super(card);
    }

    @Override
    public InvertInvent copy() {
        return new InvertInvent(this);
    }
}

class InvertEffect extends OneShotEffect {

    public InvertEffect() {
        super(Outcome.Benefit);
        this.staticText = "Switch the power and toughness of "
                + "each of up to two target creatures until end of turn.";
    }

    public InvertEffect(final InvertEffect effect) {
        super(effect);
    }

    @Override
    public InvertEffect copy() {
        return new InvertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            ContinuousEffect effect = new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetId, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class InventEffect extends OneShotEffect {

    private static final FilterCard filter1 = new FilterCard("instant card");
    private static final FilterCard filter2 = new FilterCard("sorcery card");

    static {
        filter1.add(new CardTypePredicate(CardType.INSTANT));
        filter2.add(new CardTypePredicate(CardType.SORCERY));
    }

    public InventEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for an instant card "
                + "and/or a sorcery card, reveal them, "
                + "put them into your hand, then shuffle your library.";
    }

    public InventEffect(final InventEffect effect) {
        super(effect);
    }

    @Override
    public InventEffect copy() {
        return new InventEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        TargetCardInLibrary target = new TargetCardInLibrary(filter1);
        if (player.searchLibrary(target, game, false)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                cards.add(card);
            }
        }
        target = new TargetCardInLibrary(filter2);
        if (player.searchLibrary(target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                cards.add(card);
            }
        }
        player.revealCards(source, cards, game);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
