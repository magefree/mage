
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AquamorphEntity extends CardImpl {

    public AquamorphEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Aquamorph Entity enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new AquamorphEntityReplacementEffect());
        ability.setWorksFaceDown(true);
        this.addAbility(ability);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}")));
    }

    private AquamorphEntity(final AquamorphEntity card) {
        super(card);
    }

    @Override
    public AquamorphEntity copy() {
        return new AquamorphEntity(this);
    }
}

class AquamorphEntityReplacementEffect extends ReplacementEffectImpl {

    private static final String choice51 = "a 5/1 creature";
    private static final String choice15 = "a 1/5 creature";

    AquamorphEntityReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as {this} enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5";
    }

    private AquamorphEntityReplacementEffect(AquamorphEntityReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case TURNFACEUP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
                if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.TURNFACEUP) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent;
        if (event instanceof EntersTheBattlefieldEvent) {
            permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        } else {
            permanent = game.getPermanent(event.getTargetId());
        }
        if (permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose what the creature becomes to");
        choice.getChoices().add(choice51);
        choice.getChoices().add(choice15);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.choose(Outcome.Neutral, choice, game)) {
            discard();
            return false;
        }
        int power = 0;
        int toughness = 0;
        switch (choice.getChoice()) {
            case choice51:
                power = 5;
                toughness = 1;
                break;
            case choice15:
                power = 1;
                toughness = 5;
                break;
        }
        game.addEffect(new SetPowerToughnessSourceEffect(power, toughness, Duration.Custom, SubLayer.SetPT_7b), source);
        return false;
    }

    @Override
    public AquamorphEntityReplacementEffect copy() {
        return new AquamorphEntityReplacementEffect(this);
    }

}
