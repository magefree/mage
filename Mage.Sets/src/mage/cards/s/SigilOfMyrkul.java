package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigilOfMyrkul extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public SigilOfMyrkul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of combat on your turn, mill a card. When you do, if there are four or more creature cards in your graveyard, put a +1/+1 counter on target creature you control and it gains deathtouch until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new SigilOfMyrkulEffect(), TargetController.YOU, false
        ).addHint(hint));
    }

    private SigilOfMyrkul(final SigilOfMyrkul card) {
        super(card);
    }

    @Override
    public SigilOfMyrkul copy() {
        return new SigilOfMyrkul(this);
    }
}

class SigilOfMyrkulEffect extends OneShotEffect {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURE);

    SigilOfMyrkulEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. When you do, if there are four or more creature cards in your graveyard, " +
                "put a +1/+1 counter on target creature you control and it gains deathtouch until end of turn";
    }

    private SigilOfMyrkulEffect(final SigilOfMyrkulEffect effect) {
        super(effect);
    }

    @Override
    public SigilOfMyrkulEffect copy() {
        return new SigilOfMyrkulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.millCards(1, source, game).isEmpty()) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, "if there " +
                "are four or more creature cards in your graveyard, put a +1/+1 counter on " +
                "target creature you control and it gains deathtouch until end of turn", condition
        );
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
