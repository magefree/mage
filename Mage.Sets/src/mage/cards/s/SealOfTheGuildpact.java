package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class SealOfTheGuildpact extends CardImpl {

    public SealOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As Seal of the Guildpact enters the battlefield, choose two colors.
        this.addAbility(new AsEntersBattlefieldAbility(new SealOfTheGuildpactChooseColorEffect()));

        // Each spell you cast costs {1} less to cast for each of the chosen colors it is.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SealOfTheGuildpactCostReductionEffect()));
    }

    private SealOfTheGuildpact(final SealOfTheGuildpact card) {
        super(card);
    }

    @Override
    public SealOfTheGuildpact copy() {
        return new SealOfTheGuildpact(this);
    }
}

class SealOfTheGuildpactChooseColorEffect extends OneShotEffect {

    SealOfTheGuildpactChooseColorEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose two colors";
    }

    private SealOfTheGuildpactChooseColorEffect(final SealOfTheGuildpactChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public SealOfTheGuildpactChooseColorEffect copy() {
        return new SealOfTheGuildpactChooseColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        ChoiceColor choice1 = new ChoiceColor();
        if (controller != null && mageObject != null && controller.choose(Outcome.Benefit, choice1, game)) {
            String color1 = choice1.getChoice();
            Set<String> choices2 = new HashSet<>();
            if (!color1.equals("White")) {
                choices2.add("White");
            }
            if (!color1.equals("Blue")) {
                choices2.add("Blue");
            }
            if (!color1.equals("Black")) {
                choices2.add("Black");
            }
            if (!color1.equals("Red")) {
                choices2.add("Red");
            }
            if (!color1.equals("Green")) {
                choices2.add("Green");
            }
            ChoiceColor choice2 = new ChoiceColor();
            choice2.setChoices(choices2);
            if (!controller.choose(Outcome.Benefit, choice2, game)) {
                return false;
            }
            String color2 = choice2.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + color1 + " and " + color2 + '.');
            }
            game.getState().setValue(mageObject.getId() + "_color1", choice1.getColor());
            game.getState().setValue(mageObject.getId() + "_color2", choice2.getColor());
            ((Card) mageObject).addInfo("chosen colors", CardUtil.addToolTipMarkTags("Chosen colors: " + color1 + " and " + color2), game);
            return true;
        }
        return false;
    }
}

class SealOfTheGuildpactCostReductionEffect extends CostModificationEffectImpl {

    SealOfTheGuildpactCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Each spell you cast costs {1} less to cast for each of the chosen colors it is";
    }

    private SealOfTheGuildpactCostReductionEffect(final SealOfTheGuildpactCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        MageObject sourceObject = game.getObject(abilityToModify.getSourceId());
        if (sourceObject != null) {
            ObjectColor color1 = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color1");
            ObjectColor color2 = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color2");
            int amount = 0;
            if (color1 != null && sourceObject.getColor(game).contains(color1)) {
                amount++;
            }
            if (color2 != null && sourceObject.getColor(game).contains(color2)) {
                amount++;
            }
            if (amount > 0) {
                SpellAbility spellAbility = (SpellAbility) abilityToModify;
                CardUtil.adjustCost(spellAbility, amount);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof SpellAbility;
    }

    @Override
    public SealOfTheGuildpactCostReductionEffect copy() {
        return new SealOfTheGuildpactCostReductionEffect(this);
    }
}
