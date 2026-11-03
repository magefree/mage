package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

public final class ValMaroonedSurveyor extends CardImpl {

    public ValMaroonedSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G/W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new ValMaroonedSurveyorTriggeredAbility());
    }

    private ValMaroonedSurveyor(final ValMaroonedSurveyor card) {
        super(card);
    }

    @Override
    public ValMaroonedSurveyor copy() {
        return new ValMaroonedSurveyor(this);
    }
}

class ValMaroonedSurveyorTriggeredAbility extends TriggeredAbilityImpl {

    ValMaroonedSurveyorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(Outcome.Damage, StaticValue.get(2), TargetController.OPPONENT), false);
        addEffect(new GainLifeEffect(2).concatBy("and"));
        setTriggerPhrase("Whenever you discover, investigate, scry, surveil, or seek one or more cards, ");
    }

    private ValMaroonedSurveyorTriggeredAbility(final ValMaroonedSurveyorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValMaroonedSurveyorTriggeredAbility copy() {
        return new ValMaroonedSurveyorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DISCOVERED:
            case INVESTIGATED:
            case SCRIED:
            case SURVEILED:
            case SOUGHT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
