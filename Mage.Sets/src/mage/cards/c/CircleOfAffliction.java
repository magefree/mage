package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class CircleOfAffliction extends CardImpl {

    public CircleOfAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // As Circle of Affliction enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        Ability ability = new CircleOfAfflictionTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CircleOfAffliction(final CircleOfAffliction card) {
        super(card);
    }

    @Override
    public CircleOfAffliction copy() {
        return new CircleOfAffliction(this);
    }
}

class CircleOfAfflictionTriggeredAbility extends TriggeredAbilityImpl {

    public CircleOfAfflictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new LoseLifeTargetEffect(1), new GenericManaCost(1)), false);
        ((DoIfCostPaid) getEffects().get(0)).addEffect(new GainLifeEffect(1));
    }

    private CircleOfAfflictionTriggeredAbility(final CircleOfAfflictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent circleOfAffliction = game.getPermanentOrLKIBattlefield(getSourceId());
        if (circleOfAffliction != null && event.getTargetId().equals(getControllerId())) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(circleOfAffliction.getId() + "_color");
            if (chosenColor != null) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource != null) {
                    if (damageSource.getColor(game).shares(chosenColor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CircleOfAfflictionTriggeredAbility copy() {
        return new CircleOfAfflictionTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.";
    }
}
