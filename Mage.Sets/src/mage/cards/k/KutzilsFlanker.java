package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.CreatureLeftBattlefieldWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KutzilsFlanker extends CardImpl {

    public KutzilsFlanker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Kutzil's Flanker enters the battlefield, choose one --
        // * Put a +1/+1 counter on Kutzil's Flanker for each creature that left the battlefield under your control this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), KutzilsFlankerValue.instance, true)
                        .setText("put a +1/+1 counter on {this} for each creature that left the battlefield under your control this turn")
        );
        ability.addHint(KutzilsFlankerValue.getHint());
        ability.addWatcher(new CreatureLeftBattlefieldWatcher());

        // * You gain 2 life and scry 2.
        ability.addMode(new Mode(
                new GainLifeEffect(2)
        ).addEffect(
                new ScryEffect(2, false)
                        .concatBy("and")
        ));

        // * Exile target player's graveyard.
        ability.addMode(new Mode(
                new ExileGraveyardAllTargetPlayerEffect()
        ).addTarget(new TargetPlayer()));
        this.addAbility(ability);
    }

    private KutzilsFlanker(final KutzilsFlanker card) {
        super(card);
    }

    @Override
    public KutzilsFlanker copy() {
        return new KutzilsFlanker(this);
    }
}

enum KutzilsFlankerValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Number of creatures that left", KutzilsFlankerValue.instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CreatureLeftBattlefieldWatcher.getNumberCreatureLeft(sourceAbility.getControllerId(), game);
    }

    @Override
    public KutzilsFlankerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
