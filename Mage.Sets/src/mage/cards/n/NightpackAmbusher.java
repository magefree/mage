package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.WolfToken;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightpackAmbusher extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Wolves and Werewolves");

    static {
        filter.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    public NightpackAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Other Wolves and Werewolves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of your end step, if you didn't cast a spell this turn, create a 2/2 green Wolf creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new WolfToken()))
                .withInterveningIf(NightpackAmbusherCondition.instance));
    }

    private NightpackAmbusher(final NightpackAmbusher card) {
        super(card);
    }

    @Override
    public NightpackAmbusher copy() {
        return new NightpackAmbusher(this);
    }
}

enum NightpackAmbusherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) == 0;
    }

    @Override
    public String toString() {
        return "you didn't cast a spell this turn";
    }
}
