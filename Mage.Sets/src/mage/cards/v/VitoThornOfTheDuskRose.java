package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponent;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.hint.StaticHint;

/**
 * @author TheElk801
 */
public final class VitoThornOfTheDuskRose extends CardImpl {

    public VitoThornOfTheDuskRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you gain life, target opponent loses that much life.
        this.addAbility(new VitoThornOfTheDuskRoseTriggeredAbility());

        // {3}{B}{B}: Creatures you control gain lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ), new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private VitoThornOfTheDuskRose(final VitoThornOfTheDuskRose card) {
        super(card);
    }

    @Override
    public VitoThornOfTheDuskRose copy() {
        return new VitoThornOfTheDuskRose(this);
    }
}

class VitoThornOfTheDuskRoseTriggeredAbility extends TriggeredAbilityImpl {

    VitoThornOfTheDuskRoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addTarget(new TargetOpponent());
    }

    private VitoThornOfTheDuskRoseTriggeredAbility(final VitoThornOfTheDuskRoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VitoThornOfTheDuskRoseTriggeredAbility copy() {
        return new VitoThornOfTheDuskRoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            MageObject mageObject = game.getObject(event.getSourceId());
            this.getEffects().clear();
            this.getHints().clear();
            this.addHint(new StaticHint("Lose life amount: " + event.getAmount()));
            if (mageObject != null) {
                this.addHint(new StaticHint("Caused by: " + mageObject.getLogName()));
            }
            this.addEffect(new LoseLifeTargetEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, target opponent loses that much life.";
    }
}
