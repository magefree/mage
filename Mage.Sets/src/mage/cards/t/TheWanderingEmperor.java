package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SamuraiToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWanderingEmperor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TheWanderingEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // As long as The Wandering Emperor entered the battlefield this turn, you may activate her loyalty abilities any time you could cast an instant.
        this.addAbility(new SimpleStaticAbility(new TheWanderingEmperorEffect()));

        // +1: Put a +1/+1 counter on up to one target creature. It gains first strike until end of turn.
        Ability ability = new LoyaltyAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        );
        ability.addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains first strike until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −1: Create a 2/2 white Samurai creature token with vigilance.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SamuraiToken()), -1));

        // −2: Exile target tapped creature. You gain 2 life.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TheWanderingEmperor(final TheWanderingEmperor card) {
        super(card);
    }

    @Override
    public TheWanderingEmperor copy() {
        return new TheWanderingEmperor(this);
    }
}

class TheWanderingEmperorEffect extends AsThoughEffectImpl {

    TheWanderingEmperorEffect() {
        super(AsThoughEffectType.ACTIVATE_AS_INSTANT, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} entered the battlefield this turn, " +
                "you may activate her loyalty abilities any time you could cast an instant";
    }

    private TheWanderingEmperorEffect(final TheWanderingEmperorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheWanderingEmperorEffect copy() {
        return new TheWanderingEmperorEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && permanent.getTurnsOnBattlefield() == 0
                && affectedAbility.isControlledBy(source.getControllerId())
                && affectedAbility.getSourceId().equals(source.getSourceId())
                && affectedAbility instanceof LoyaltyAbility;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
