package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */

public class CounterTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {


    public CounterTargetAndSearchGraveyardHandLibraryEffect() {
        this(false, "its controller's", "all cards with the same name as that spell");
    }

    public CounterTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        this(graveyardExileOptional, searchWhatText, searchForText, false);
    }

    public CounterTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText, boolean drawForEachHandCard) {
        super(graveyardExileOptional, searchWhatText, searchForText, drawForEachHandCard);
    }

    protected CounterTargetAndSearchGraveyardHandLibraryEffect(final CounterTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
    }

    @Override
    public CounterTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new CounterTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        if (source.getTargets().get(0) instanceof TargetSpell) {
            UUID objectId = source.getFirstTarget();
            StackObject stackObject = game.getStack().getStackObject(objectId);
            if (stackObject != null) {
                String cardName = stackObject.getName();
                UUID searchPlayerId = stackObject.getControllerId();
                result = game.getStack().counter(objectId, source, game);
                // 5/1/2008: If the targeted spell can't be countered (it's Vexing Shusher, for example),
                // that spell will remain on the stack. Counterbore will continue to resolve. You still
                // get to search for and exile all other cards with that name.
                this.applySearchAndExile(game, source, cardName, searchPlayerId);
            }
        }

        return result;
    }

    @Override
    public String getText(Mode mode) {
        return "counter " +
                getTargetPointer().describeTargets(mode.getTargets(), "that spell") + ". " +
                CardUtil.getTextWithFirstCharUpperCase(super.getText(mode));
    }
}
