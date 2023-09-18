package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000 & L_J
 */
public final class RealityTwist extends CardImpl {

    public RealityTwist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}{U}");

        // Cumulative upkeep-Pay {1}{U}{U}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}{U}{U}")));

        // If tapped for mana, Plains produce {R}, Swamps produce {G}, Mountains produce {W}, and Forests produce {B} instead of any other type.
        this.addAbility(new SimpleStaticAbility(new RealityTwistEffect()));
    }

    private RealityTwist(final RealityTwist card) {
        super(card);
    }

    @Override
    public RealityTwist copy() {
        return new RealityTwist(this);
    }
}

class RealityTwistEffect extends ReplacementEffectImpl {

    RealityTwistEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If tapped for mana, Plains produce {R}, Swamps produce {G}, " +
                "Mountains produce {W}, and Forests produce {B} instead of any other type";
    }

    private RealityTwistEffect(final RealityTwistEffect effect) {
        super(effect);
    }

    @Override
    public RealityTwistEffect copy() {
        return new RealityTwistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        TappedForManaEvent manaEvent = (TappedForManaEvent) event;
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = manaEvent.getPermanent();
        if (controller == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a color to produce");
        if (permanent.hasSubtype(SubType.PLAINS, game)) {
            choice.getChoices().add("Red");
        }
        if (permanent.hasSubtype(SubType.SWAMP, game)) {
            choice.getChoices().add("Green");
        }
        if (permanent.hasSubtype(SubType.MOUNTAIN, game)) {
            choice.getChoices().add("White");
        }
        if (permanent.hasSubtype(SubType.FOREST, game)) {
            choice.getChoices().add("Black");
        }
        String chosenColor;
        if (choice.getChoices().size() == 1) {
            chosenColor = choice.getChoices().iterator().next();
        } else {
            controller.choose(Outcome.PutManaInPool, choice, game);
            chosenColor = choice.getChoice();
        }
        if (chosenColor == null) {
            return false;
        }
        Mana mana = manaEvent.getMana();
        int amount = mana.count();
        switch (chosenColor) {
            case "White":
                mana.setToMana(Mana.WhiteMana(amount));
                break;
            case "Black":
                mana.setToMana(Mana.BlackMana(amount));
                break;
            case "Red":
                mana.setToMana(Mana.RedMana(amount));
                break;
            case "Green":
                mana.setToMana(Mana.GreenMana(amount));
                break;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null
                && (permanent.hasSubtype(SubType.PLAINS, game)
                || permanent.hasSubtype(SubType.SWAMP, game)
                || permanent.hasSubtype(SubType.MOUNTAIN, game)
                || permanent.hasSubtype(SubType.FOREST, game));
    }
}
