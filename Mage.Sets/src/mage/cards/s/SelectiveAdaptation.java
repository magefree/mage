package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.*;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelectiveAdaptation extends CardImpl {

    public SelectiveAdaptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Reveal the top seven cards of your library. Choose from among them a card with flying, a card with first strike, and so on for double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance. Put one of the chosen cards onto the battlefield, the other chosen cards into your hand, and the rest into your graveyard.
        this.getSpellAbility().addEffect(new SelectiveAdaptationEffect());
    }

    private SelectiveAdaptation(final SelectiveAdaptation card) {
        super(card);
    }

    @Override
    public SelectiveAdaptation copy() {
        return new SelectiveAdaptation(this);
    }
}

class SelectiveAdaptationEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("a card to put onto the battlefield");

    private enum AbilitySelector {
        FLYING(FlyingAbility.class, "flying"),
        FIRST_STRIKE(FirstStrikeAbility.class, "first strike"),
        DOUBLE_STRIKE(DoubleStrikeAbility.class, "double strike"),
        DEATHTOUCH(DeathtouchAbility.class, "deathtouch"),
        HASTE(HasteAbility.class, "haste"),
        HEXPROOF(HexproofBaseAbility.class, "hexproof"),
        INDESTRUCTIBLE(IndestructibleAbility.class, "indestructible"),
        LIFELINK(LifelinkAbility.class, "lifelink"),
        MENACE(MenaceAbility.class, "menace"),
        REACH(ReachAbility.class, "reach"),
        TRAMPLE(TrampleAbility.class, "trample"),
        VIGILANCE(VigilanceAbility.class, "vigilance");

        private final Class abilityClass;
        private final String abilityName;
        private final FilterCard filter;

        AbilitySelector(Class abilityClass, String abilityName) {
            this.abilityClass = abilityClass;
            this.abilityName = abilityName;
            this.filter = new FilterCard("card with " + abilityName);
            filter.add(new AbilityPredicate(abilityClass));
        }

        private TargetCardInLibrary makeTarget() {
            return new TargetCardInLibrary(0, 1, filter);
        }
    }

    SelectiveAdaptationEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top seven cards of your library. Choose from among them a card with flying, " +
                "a card with first strike, and so on for double strike, deathtouch, haste, hexproof, indestructible, " +
                "lifelink, menace, reach, trample, and vigilance. Put one of the chosen cards onto the battlefield, " +
                "the other chosen cards into your hand, and the rest into your graveyard.";
    }

    private SelectiveAdaptationEffect(final SelectiveAdaptationEffect effect) {
        super(effect);
    }

    @Override
    public SelectiveAdaptationEffect copy() {
        return new SelectiveAdaptationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards top7 = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        Cards toGrave = top7.copy();
        player.revealCards(source, toGrave, game);
        Cards toHand = new CardsImpl();
        if (toGrave.isEmpty()) {
            return false;
        }
        for (AbilitySelector abilitySelector : AbilitySelector.values()) {
            if (toGrave.count(abilitySelector.filter, game) < 1) {
                continue;
            }
            TargetCard target = abilitySelector.makeTarget();
            player.choose(Outcome.DrawCard, top7, target, source, game);
            toHand.add(target.getFirstTarget());
            toGrave.remove(target.getFirstTarget());
        }
        if (toGrave.isEmpty()) {
            return false;
        }
        if (toHand.count(filter, game) > 0) {
            TargetCard target = new TargetCardInLibrary(filter);
            player.choose(Outcome.PutCreatureInPlay, toHand, target, source, game);
            Card toBattlefield = game.getCard(target.getFirstTarget());
            if (toBattlefield != null
                    && player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game)
                    && Zone.BATTLEFIELD.equals(game.getState().getZone(toBattlefield.getId()))) {
                toHand.remove(toBattlefield);
            }
        }
        player.moveCards(toHand, Zone.HAND, source, game);
        player.moveCards(toGrave, Zone.GRAVEYARD, source, game);
        return true;
    }
}
