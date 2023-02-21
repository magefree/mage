package mage.cards.r;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author @stwalsh4118
 */
public final class RampagingGrowth extends CardImpl {

    public RampagingGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");
        

        // Search your library for a basic land card, put it on the battlefield, then shuffle. Until end of turn, that land becomes a 4/3 Insect creature with reach and haste. It's still a land.
        this.getSpellAbility().addEffect(new RampagingGrowthEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND)).setText("Search your library for a basic land card, put it on the battlefield, then shuffle. Until end of turn, that land becomes a 4/3 Insect creature with reach and haste. It's still a land."));
    }

    private RampagingGrowth(final RampagingGrowth card) {
        super(card);
    }

    @Override
    public RampagingGrowth copy() {
        return new RampagingGrowth(this);
    }
}

class RampagingGrowthEffect extends SearchEffect {

    protected boolean tapped;
    protected boolean forceShuffle;
    protected boolean optional;

    public RampagingGrowthEffect(TargetCardInLibrary target) {
        this(target, false, true, Outcome.PutCardInPlay);
    }

    public RampagingGrowthEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, true, Outcome.PutCardInPlay);
    }

    public RampagingGrowthEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle) {
        this(target, tapped, forceShuffle, Outcome.PutCardInPlay);
    }

    public RampagingGrowthEffect(TargetCardInLibrary target, boolean tapped, Outcome outcome) {
        this(target, tapped, true, outcome);
    }

    public RampagingGrowthEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, Outcome outcome) {
        this(target, tapped, forceShuffle, false, outcome);
    }

    public RampagingGrowthEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, boolean optional, Outcome outcome) {
        super(target, outcome);
        this.tapped = tapped;
        this.forceShuffle = forceShuffle;
        this.optional = optional;
        staticText = (optional ? "you may " : "")
                + "search your library for "
                + target.getDescription()
                + (forceShuffle ? ", " : " and ")
                + (target.getMaxNumberOfTargets() > 1 ? "put them onto the battlefield" : "put it onto the battlefield")
                + (tapped ? " tapped" : "")
                + (forceShuffle ? ", then shuffle" : ". If you do, shuffle");
    }

    public RampagingGrowthEffect(final RampagingGrowthEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.forceShuffle = effect.forceShuffle;
        this.optional = effect.optional;
    }

    @Override
    public RampagingGrowthEffect copy() {
        return new RampagingGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = Collections.<Card>emptySet();
        if (player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                cards = new CardsImpl(target.getTargets()).getCards(game);
                player.moveCards(cards,Zone.BATTLEFIELD, source, game, tapped, false, false, null);
            }
            player.shuffleLibrary(source, game);

            // Until end of turn, that land becomes a 4/3 Insect creature with reach and haste. It's still a land.
            if(!cards.isEmpty()) {
                for (Card card : cards) {
                    BecomesCreatureTargetEffect effect = new BecomesCreatureTargetEffect(new InsectToken(), false, true, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
        }

        return true;

    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

    private static class InsectToken extends TokenImpl {

        InsectToken() {
            super("Insect", "4/3 Insect creature with reach and haste. It's still a land");
            cardType.add(CardType.CREATURE);
            subtype.add(SubType.INSECT);
            power = new MageInt(4);
            toughness = new MageInt(3);
            addAbility(ReachAbility.getInstance());
            addAbility(HasteAbility.getInstance());
        }
        public InsectToken(final InsectToken token) {
            super(token);
        }

        public InsectToken copy() {
            return new InsectToken(this);
        }
    }
}
