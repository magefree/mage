
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class PrimalClay extends CardImpl {

    public PrimalClay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PrimalPlasmaReplacementEffect()));
    }

    private PrimalClay(final PrimalClay card) {
        super(card);
    }

    @Override
    public PrimalClay copy() {
        return new PrimalClay(this);
    }

    static class PrimalPlasmaReplacementEffect extends ReplacementEffectImpl {

        private static final String choice33 = "a 3/3 artifact creature";
        private static final String choice22 = "a 2/2 artifact creature with flying";
        private static final String choice16 = "a 1/6 artifact creature with defender";

        public PrimalPlasmaReplacementEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Benefit);
            staticText = "As {this} enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types";
        }

        public PrimalPlasmaReplacementEffect(PrimalPlasmaReplacementEffect effect) {
            super(effect);
        }

        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (!event.getTargetId().equals(source.getSourceId())) {
                return false;
            }
            Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
            return sourcePermanent != null && !sourcePermanent.isFaceDown(game);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent == null) {
                return false;
            }
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose what " + permanent.getIdName() + " becomes to");
            choice.getChoices().add(choice33);
            choice.getChoices().add(choice22);
            choice.getChoices().add(choice16);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && !controller.choose(Outcome.Neutral, choice, game)) {
                return false;
            }
            int power = 0;
            int toughness = 0;
            switch (choice.getChoice()) {
                case choice33:
                    power = 3;
                    toughness = 3;
                    break;
                case choice22:
                    power = 2;
                    toughness = 2;
                    game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.Custom), source);
                    break;
                case choice16:
                    power = 1;
                    toughness = 6;
                    game.addEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance(), Duration.Custom), source);
                    break;
            }
            game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield, true), source);
            return false;
        }

        @Override
        public PrimalPlasmaReplacementEffect copy() {
            return new PrimalPlasmaReplacementEffect(this);
        }

    }
}
