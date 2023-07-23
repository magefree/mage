package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
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
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}")));
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

    private static final String choice51 = "5/1";
    private static final String choice15 = "1/5";

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
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                if (!event.getTargetId().equals(source.getSourceId())) {
                    return false;
                }
                Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
                return sourcePermanent != null && !sourcePermanent.isFaceDown(game);
            case TURNFACEUP:
                return event.getTargetId().equals(source.getSourceId());
            default:
                return false;
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent;
        if (event instanceof EntersTheBattlefieldEvent) {
            permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        } else {
            permanent = game.getPermanent(event.getTargetId());
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose what " + permanent.getIdName() + " becomes as it " +
                (event instanceof EntersTheBattlefieldEvent ? "enters the battlefield" : "is turned face up"));
        choice.getChoices().add(choice51);
        choice.getChoices().add(choice15);
        if (!controller.choose(Outcome.Neutral, choice, game)) {
            return false;
        }
        int power;
        int toughness;
        switch (choice.getChoice()) {
            case choice51:
                power = 5;
                toughness = 1;
                break;
            case choice15:
                power = 1;
                toughness = 5;
                break;
            default:
                return false;
        }
        /* TODO: The chosen characteristics are copiable values; make sure this is handled correctly
         * 707.2. When copying an object, the copy acquires the copiable values of the original object's characteristics...
         * The copiable values are the values derived from the text printed on the object
         * (that text being name, mana cost, color indicator, card type, subtype, supertype, rules text, power, toughness, and/or loyalty),
         * as modified by other copy effects, by its face-down status,
         * and by "as ... enters the battlefield" and "as ... is turned face up" abilities
         * that set power and toughness (and may also set additional characteristics).
         */
        game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
        return false;
    }

    @Override
    public AquamorphEntityReplacementEffect copy() {
        return new AquamorphEntityReplacementEffect(this);
    }

}
