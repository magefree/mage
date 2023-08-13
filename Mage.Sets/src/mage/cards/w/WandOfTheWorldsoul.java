package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WandOfTheWorldsoul extends CardImpl {

    public WandOfTheWorldsoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // Wand of the Worldsoul enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: The next spell you cast this turn has convoke.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WandOfTheWorldsoulEffect(), new TapSourceCost()));
    }

    private WandOfTheWorldsoul(final WandOfTheWorldsoul card) {
        super(card);
    }

    @Override
    public WandOfTheWorldsoul copy() {
        return new WandOfTheWorldsoul(this);
    }
}
class WandOfTheWorldsoulEffect extends ContinuousEffectImpl {

    private int spellsCast;

    WandOfTheWorldsoulEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "the next spell you cast this turn has convoke";
    }

    private WandOfTheWorldsoulEffect(final WandOfTheWorldsoulEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }

        //check if a spell was cast before
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }

        //check cast for spell and add convoke
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    game.getState().addOtherAbility(spell.getCard(), new ConvokeAbility());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public WandOfTheWorldsoulEffect copy() {
        return new WandOfTheWorldsoulEffect(this);
    }
}
