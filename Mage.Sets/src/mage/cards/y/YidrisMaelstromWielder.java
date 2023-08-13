
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class YidrisMaelstromWielder extends CardImpl {

    public YidrisMaelstromWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Yidris, Maelstrom Wielder deals combat damage to a player, as you cast spells from your hand this turn, they gain cascade.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new YidrisMaelstromWielderGainCascadeEffect(), false));
    }

    private YidrisMaelstromWielder(final YidrisMaelstromWielder card) {
        super(card);
    }

    @Override
    public YidrisMaelstromWielder copy() {
        return new YidrisMaelstromWielder(this);
    }
}

class YidrisMaelstromWielderGainCascadeEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    public YidrisMaelstromWielderGainCascadeEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "as you cast spells from your hand this turn, they gain cascade";
    }

    public YidrisMaelstromWielderGainCascadeEffect(final YidrisMaelstromWielderGainCascadeEffect effect) {
        super(effect);
    }

    @Override
    public YidrisMaelstromWielderGainCascadeEffect copy() {
        return new YidrisMaelstromWielderGainCascadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    if (spell.getFromZone() == Zone.HAND) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
