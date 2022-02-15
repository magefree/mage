package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.AjanisPridemateToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjaniStrengthOfThePride extends CardImpl {

    public AjaniStrengthOfThePride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(5);

        // +1: You gain life equal to the number of creatures you control plus the number of planeswalkers you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(
                AjaniStrengthOfThePrideValue.instance, "You gain life equal to " +
                "the number of creatures you control plus the number of planeswalkers you control."
        ), 1));

        // −2: Create a 2/2 white Cat Soldier creature token named Ajani's Pridemate with "Whenever you gain life, put a +1/+1 counter on Ajani's Pridemate."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AjanisPridemateToken()), -2));

        // 0: If you have at least 15 life more than your starting life total, exile Ajani, Strength of the Pride and each artifact and creature your opponents control.
        this.addAbility(new LoyaltyAbility(new AjaniStrengthOfThePrideEffect(), 0));
    }

    private AjaniStrengthOfThePride(final AjaniStrengthOfThePride card) {
        super(card);
    }

    @Override
    public AjaniStrengthOfThePride copy() {
        return new AjaniStrengthOfThePride(this);
    }
}

enum AjaniStrengthOfThePrideValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                sourceAbility.getControllerId(), game
        ).size() + game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_PLANESWALKER,
                sourceAbility.getControllerId(), game
        ).size();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class AjaniStrengthOfThePrideEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    AjaniStrengthOfThePrideEffect() {
        super(Outcome.Benefit);
        staticText = "If you have at least 15 life more than your starting life total, " +
                "exile {this} and each artifact and creature your opponents control.";
    }

    private AjaniStrengthOfThePrideEffect(final AjaniStrengthOfThePrideEffect effect) {
        super(effect);
    }

    @Override
    public AjaniStrengthOfThePrideEffect copy() {
        return new AjaniStrengthOfThePrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLife() < game.getStartingLife() + 15) {
            return false;
        }
        new ExileSourceEffect().apply(game, source);
        return new ExileAllEffect(filter).apply(game, source);
    }
}