package mage.cards.h;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HallOfGemstone extends CardImpl {

    public HallOfGemstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        this.supertype.add(SuperType.WORLD);

        // At the beginning of each player's upkeep, that player chooses a color. Until end of turn, lands tapped for mana produce mana of the chosen color instead of any other color.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HallOfGemstoneEffect(), TargetController.ACTIVE, false));
    }

    private HallOfGemstone(final HallOfGemstone card) {
        super(card);
    }

    @Override
    public HallOfGemstone copy() {
        return new HallOfGemstone(this);
    }
}

class HallOfGemstoneEffect extends ReplacementEffectImpl {

    HallOfGemstoneEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "that player chooses a color. Until end of turn, lands tapped for mana produce mana of the chosen color instead of any other color";
    }

    private HallOfGemstoneEffect(final HallOfGemstoneEffect effect) {
        super(effect);
    }

    @Override
    public HallOfGemstoneEffect copy() {
        return new HallOfGemstoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent mageObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null || mageObject == null) {
            return;
        }
        ChoiceColor choice = new ChoiceColor();
        if (!player.choose(outcome, choice, game)) {
            discard();
            return;
        }
        game.informPlayers(mageObject.getLogName() + ": " + player.getLogName() + " has chosen " + choice.getChoice());
        game.getState().setValue(mageObject.getId() + "_color", choice.getColor());
        mageObject.addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen color: " + choice.getChoice()), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ObjectColor colorChosen = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (colorChosen == null) {
            return false;
        }
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        // 8/23/2016 	Colorless mana added to a player's mana pool isn't affected.
        int genericAmount = mana.getGeneric();
        int colorlessAmount = mana.getColorless();
        int coloredAmount = mana.countColored();
        switch (colorChosen.getOneColoredManaSymbol()) {
            case W:
                mana.setToMana(Mana.WhiteMana(coloredAmount));
                break;
            case U:
                mana.setToMana(Mana.BlueMana(coloredAmount));
                break;
            case B:
                mana.setToMana(Mana.BlackMana(coloredAmount));
                break;
            case R:
                mana.setToMana(Mana.RedMana(coloredAmount));
                break;
            case G:
                mana.setToMana(Mana.GreenMana(coloredAmount));
                break;
        }
        mana.setGeneric(genericAmount);
        mana.setColorless(colorlessAmount);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game);
    }
}
