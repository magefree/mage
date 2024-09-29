
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.common.FirstSpellCastThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class MaelstromNexus extends CardImpl {

    public MaelstromNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");

        // The first spell you cast each turn has cascade.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaelstromNexusGainCascadeFirstSpellEffect()), new FirstSpellCastThisTurnWatcher());
    }

    private MaelstromNexus(final MaelstromNexus card) {
        super(card);
    }

    @Override
    public MaelstromNexus copy() {
        return new MaelstromNexus(this);
    }
}

class MaelstromNexusGainCascadeFirstSpellEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    public MaelstromNexusGainCascadeFirstSpellEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "The first spell you cast each turn has cascade";
    }

    private MaelstromNexusGainCascadeFirstSpellEffect(final MaelstromNexusGainCascadeFirstSpellEffect effect) {
        super(effect);
    }

    @Override
    public MaelstromNexusGainCascadeFirstSpellEffect copy() {
        return new MaelstromNexusGainCascadeFirstSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    FirstSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstSpellCastThisTurnWatcher.class);
                    if (watcher != null && spell.getId().equals(watcher.getIdOfFirstCastSpell(source.getControllerId()))) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
