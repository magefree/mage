package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterObject;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author miesma
 */
public final class GollumRiddleMaster extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell with mana value of the chosen quality");

    static {
        filter.add(GollumRiddleMasterPredicate.instance);
    }

    public GollumRiddleMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As Gollum enters, choose odd or even.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.ODD, ModeChoice.EVEN)));

        // Whenever an opponent casts a spell with mana value of the chosen quality,
        // choose one that hasn’t been chosen
        // - Put a +1/+1 counter on Gollum.
        // - Each opponent loses 2 life and you gain 2 life.
        // - Draw a card.
        Ability ability = new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false);
        ability.getModes().setLimitUsageByOnce(false);
        ability.setModeTag("+1/+1 counter");
        Mode drain = new Mode(new LoseLifeOpponentsEffect(2)).setModeTag("drain life");
        drain.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addMode(drain);
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)).setModeTag("draw"));
        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);

    }

    private GollumRiddleMaster(final GollumRiddleMaster card) {
        super(card);
    }

    @Override
    public GollumRiddleMaster copy() {
        return new GollumRiddleMaster(this);
    }
}

enum GollumRiddleMasterPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    private static final FilterObject oddFilter = new FilterObject("odd mana values");
    private static final FilterObject evenFilter = new FilterObject("even mana values");

    static {
        oddFilter.add(ManaValueParityPredicate.ODD);
        evenFilter.add(ManaValueParityPredicate.EVEN);
    }

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        Object obj = game.getState().getValue(
                input.getSource().getSourceId() +
                        "_modeChoice");
        if (obj == null) {
            return false;
        }
        String value = (String) obj;
        if (value.equals("odd")) {
            return oddFilter.match(input.getObject(), game);
        } else if (value.equals("even")) {
            return evenFilter.match(input.getObject(), game);
        }
        return false;
    }
}

