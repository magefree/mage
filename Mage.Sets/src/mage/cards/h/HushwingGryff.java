
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class HushwingGryff extends CardImpl {

    public HushwingGryff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HIPPOGRIFF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HushwingGryffEffect()));
    }

    private HushwingGryff(final HushwingGryff card) {
        super(card);
    }

    @Override
    public HushwingGryff copy() {
        return new HushwingGryff(this);
    }
}

class HushwingGryffEffect extends ContinuousRuleModifyingEffectImpl {

    HushwingGryffEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, true);
        staticText = "Creatures entering the battlefield don't cause abilities to trigger";
    }

    HushwingGryffEffect(final HushwingGryffEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject enteringObject = game.getObject(event.getSourceId());
        MageObject sourceObject = game.getObject(source);
        Ability ability = (Ability) getValue("targetAbility");
        if (enteringObject != null && sourceObject != null && ability != null) {
            MageObject abilitObject = game.getObject(ability.getSourceId());
            if (abilitObject != null) {
                return sourceObject.getLogName() + " prevented ability of " + abilitObject.getLogName()
                        + " to trigger for " + enteringObject.getLogName() + " entering the battlefield.";
            }
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if (ability != null && ability.getAbilityType() == AbilityType.TRIGGERED) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature(game)) {
                return (((TriggeredAbility) ability).checkTrigger(event, game));
            }
        }
        return false;
    }

    @Override
    public HushwingGryffEffect copy() {
        return new HushwingGryffEffect(this);
    }

}
