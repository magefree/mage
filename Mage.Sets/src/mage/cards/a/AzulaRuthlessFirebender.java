package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.OptionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.*;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

/**
 *
 * @author anonymous
 */
public final class AzulaRuthlessFirebender extends CardImpl {

    public AzulaRuthlessFirebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Firebending 1
        this.addAbility(new FirebendingAbility(1));

        // Whenever Azula attacks, you may discard a card. Then you get an experience counter for each player who discarded a card this turn.
        Ability ability = new AttacksTriggeredAbility(new OptionalOneShotEffect(new DiscardControllerEffect(1)));
        ability.addEffect(new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), AzulaRuthlessFirebenderValue.instance, TargetController.YOU).setText("Then you get an experience counter for each player who discarded a card this turn."));
        this.addAbility(ability, new DiscardedCardWatcher());

        // {2}{B}: Until end of turn, Azula gets +1/+1 for each experience counter you have and gains menace.
        ability = new SimpleActivatedAbility(new BoostSourceEffect(SourceControllerCountersCount.EXPERIENCE, SourceControllerCountersCount.EXPERIENCE, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn).setText("and gains menace"));
        this.addAbility(ability);
    }

    private AzulaRuthlessFirebender(final AzulaRuthlessFirebender card) {
        super(card);
    }

    @Override
    public AzulaRuthlessFirebender copy() {
        return new AzulaRuthlessFirebender(this);
    }
}

enum AzulaRuthlessFirebenderValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int playersDiscardedThisTurn = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            if (DiscardedCardWatcher.checkPlayerDiscarded(playerId, game)) {
                playersDiscardedThisTurn += 1;
            }
        }
        return playersDiscardedThisTurn;
    }

    @Override
    public AzulaRuthlessFirebenderValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "for each player who discarded a card this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}