
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
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PrimalPlasma extends CardImpl {

    public PrimalPlasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PrimalPlasmaReplacementEffect()));
    }

    private PrimalPlasma(final PrimalPlasma card) {
        super(card);
    }

    @Override
    public PrimalPlasma copy() {
        return new PrimalPlasma(this);
    }

    static class PrimalPlasmaReplacementEffect extends ReplacementEffectImpl {

        private static final String choice33 = "a 3/3 creature";
        private static final String choice22 = "a 2/2 creature with flying";
        private static final String choice16 = "a 1/6 creature with defender";

        public PrimalPlasmaReplacementEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Benefit);
            staticText = "As {this} enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender";
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
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
                return sourcePermanent != null && !sourcePermanent.isFaceDown(game);
            }
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
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
                choice.getChoices().add(choice22);
                choice.getChoices().add(choice16);
                if (!controller.choose(Outcome.Neutral, choice, game)) {
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
                game.addEffect(new SetBasePowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield, SubLayer.CharacteristicDefining_7a), source);
            }
            return false;

        }

        @Override
        public PrimalPlasmaReplacementEffect copy() {
            return new PrimalPlasmaReplacementEffect(this);
        }

    }
}
