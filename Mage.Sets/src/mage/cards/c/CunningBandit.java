package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CunningBandit extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.KI, 2);

    public CunningBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Azamuki, Treachery Incarnate";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Cunning Bandit.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, true));

        // At the beginning of the end step, if there are two or more ki counters on Cunning Bandit, you may flip it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new FlipSourceEffect(new AzamukiTreacheryIncarnate()).setText("flip it"), true, condition
        ));
    }

    private CunningBandit(final CunningBandit card) {
        super(card);
    }

    @Override
    public CunningBandit copy() {
        return new CunningBandit(this);
    }
}

class AzamukiTreacheryIncarnate extends TokenImpl {

    AzamukiTreacheryIncarnate() {
        super("Azamuki, Treachery Incarnate", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(5);
        toughness = new MageInt(2);

        // Remove a ki counter from Azamuki, Treachery Incarnate: Gain control of target creature until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainControlTargetEffect(Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AzamukiTreacheryIncarnate(final AzamukiTreacheryIncarnate token) {
        super(token);
    }

    public AzamukiTreacheryIncarnate copy() {
        return new AzamukiTreacheryIncarnate(this);
    }
}
