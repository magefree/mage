package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.token.TamiyosNotebookToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TamiyoCompleatedSage extends CardImpl {

    public TamiyoCompleatedSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G/U/P}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);
        this.setStartingLoyalty(5);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Tap up to one target artifact or creature. It doesn't untap during its controller's next untap step.
        Ability ability = new LoyaltyAbility(new TapTargetEffect(), 1);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("it"));
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE
        ));
        this.addAbility(ability);

        // −X: Exile target nonland permanent card with mana value X from your graveyard. Create a token that's a copy of that card.
        this.addAbility(new LoyaltyAbility(new TamiyoCompleatedSageEffect()).setTargetAdjuster(TamiyoCompleatedSageAdjuster.instance));

        // −7: Create Tamiyo's Notebook, a legendary colorless artifact token with "Spells you cast cost {2} less to cast" and "{T}: Draw a card."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new TamiyosNotebookToken()), -7));
    }

    private TamiyoCompleatedSage(final TamiyoCompleatedSage card) {
        super(card);
    }

    @Override
    public TamiyoCompleatedSage copy() {
        return new TamiyoCompleatedSage(this);
    }
}

enum TamiyoCompleatedSageAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = GetXLoyaltyValue.instance.calculate(game, ability, null);
        FilterCard filter = new FilterPermanentCard("nonland permanent card with mana value " + xValue);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}

class TamiyoCompleatedSageEffect extends OneShotEffect {

    TamiyoCompleatedSageEffect() {
        super(Outcome.Benefit);
        staticText = "exile target nonland permanent card with mana value X " +
                "from your graveyard. Create a token that's a copy of that card";
    }

    private TamiyoCompleatedSageEffect(final TamiyoCompleatedSageEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoCompleatedSageEffect copy() {
        return new TamiyoCompleatedSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect().setSavedPermanent(
                new PermanentCard(card, source.getControllerId(), game)
        ).apply(game, source);
    }
}
// aqua got norted
