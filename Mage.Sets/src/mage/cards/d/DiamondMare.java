package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class DiamondMare extends CardImpl {

    public DiamondMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As Diamond Mare enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever you cast a spell of the chosen color, you gain 1 life.
        this.addAbility(new DiamondMareTriggeredAbility());
    }

    private DiamondMare(final DiamondMare card) {
        super(card);
    }

    @Override
    public DiamondMare copy() {
        return new DiamondMare(this);
    }
}

class DiamondMareTriggeredAbility extends TriggeredAbilityImpl {

    public DiamondMareTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
    }

    public DiamondMareTriggeredAbility(final DiamondMareTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (spell != null && color != null && spell.getColor(game).shares(color)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell of the chosen color, you gain 1 life.";
    }

    @Override
    public DiamondMareTriggeredAbility copy() {
        return new DiamondMareTriggeredAbility(this);
    }
}
