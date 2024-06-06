package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
 * @author Susucr
 */
public final class CorruptedShapeshifter extends CardImpl {

    public CorruptedShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // As Corrupted Shapeshifter enters the battlefield, it becomes your choice of a 3/3 creature with flying, a 2/5 creature with vigilance, or a 0/12 creature with defender.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CorruptedShapeshifterReplacementEffect()));
    }

    private CorruptedShapeshifter(final CorruptedShapeshifter card) {
        super(card);
    }

    @Override
    public CorruptedShapeshifter copy() {
        return new CorruptedShapeshifter(this);
    }
}

class CorruptedShapeshifterReplacementEffect extends ReplacementEffectImpl {

    private static final String choice33 = "a 3/3 creature with flying";
    private static final String choice25 = "a 2/5 creature with vigilance";
    private static final String choice012 = "a 0/12 creature with defender";

    public CorruptedShapeshifterReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As {this} enters the battlefield, it becomes your choice of a 3/3 creature with flying, "
                + "a 2/5 creature with vigilance, or a 0/12 creature with defender";
    }

    private CorruptedShapeshifterReplacementEffect(final CorruptedShapeshifterReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
            return sourcePermanent != null && !sourcePermanent.isFaceDown(game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose what " + permanent.getIdName() + " becomes to");
            choice.getChoices().add(choice33);
            choice.getChoices().add(choice25);
            choice.getChoices().add(choice012);
            if (!controller.choose(Outcome.Neutral, choice, game)) {
                return false;
            }
            int power = 0;
            int toughness = 0;
            switch (choice.getChoice()) {
                case choice33:
                    power = 3;
                    toughness = 3;
                    game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.Custom), source);
                    break;
                case choice25:
                    power = 2;
                    toughness = 5;
                    game.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.Custom), source);
                    break;
                case choice012:
                    power = 0;
                    toughness = 12;
                    game.addEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance(), Duration.Custom), source);
                    break;
            }
            game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield), source);
        }
        return false;

    }

    @Override
    public CorruptedShapeshifterReplacementEffect copy() {
        return new CorruptedShapeshifterReplacementEffect(this);
    }

}