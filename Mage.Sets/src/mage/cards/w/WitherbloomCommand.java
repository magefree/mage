package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitherbloomCommand extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("noncreature, nonland permanent with mana value 2 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public WitherbloomCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target player mills three cards, then you return a land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        this.getSpellAbility().addEffect(new WitherbloomCommandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // • Destroy target noncreature, nonland permanent with mana value 2 or less.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);

        // • Target creature gets -3/-1 until end of turn.
        mode = new Mode(new BoostTargetEffect(-3, -1));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • Target opponent loses 2 life and you gain 2 life.
        mode = new Mode(new LoseLifeTargetEffect(2));
        mode.addEffect(new GainLifeEffect(2).concatBy("and"));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private WitherbloomCommand(final WitherbloomCommand card) {
        super(card);
    }

    @Override
    public WitherbloomCommand copy() {
        return new WitherbloomCommand(this);
    }
}

class WitherbloomCommandEffect extends OneShotEffect {

    WitherbloomCommandEffect() {
        super(Outcome.ReturnToHand);
        staticText = ", then you return a land card from your graveyard to your hand";
    }

    private WitherbloomCommandEffect(final WitherbloomCommandEffect effect) {
        super(effect);
    }

    @Override
    public WitherbloomCommandEffect copy() {
        return new WitherbloomCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(1, StaticFilters.FILTER_CARD_LAND);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
