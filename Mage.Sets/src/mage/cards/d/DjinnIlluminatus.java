
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class DjinnIlluminatus extends CardImpl {

    public DjinnIlluminatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U/R}{U/R}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // <i>({U/R} can be paid with either {U} or {R}.)</i>
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DjinnIlluminatusGainReplicateEffect()));

    }

    private DjinnIlluminatus(final DjinnIlluminatus card) {
        super(card);
    }

    @Override
    public DjinnIlluminatus copy() {
        return new DjinnIlluminatus(this);
    }
}

class DjinnIlluminatusGainReplicateEffect extends ContinuousEffectImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();
    private final Map<UUID, ReplicateAbility> replicateAbilities = new HashMap<>();

    public DjinnIlluminatusGainReplicateEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost "
                + "<i>(When you cast it, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)</i>";
    }

    public DjinnIlluminatusGainReplicateEffect(final DjinnIlluminatusGainReplicateEffect effect) {
        super(effect);
        this.replicateAbilities.putAll(effect.replicateAbilities);
    }

    @Override
    public DjinnIlluminatusGainReplicateEffect copy() {
        return new DjinnIlluminatusGainReplicateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent djinn = game.getPermanent(source.getSourceId());
        if (djinn == null) {
            return false;
        }
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if ((stackObject instanceof Spell) 
                    && !stackObject.isCopy() 
                    && stackObject.isControlledBy(source.getControllerId())
                    && djinn.isControlledBy(source.getControllerId())  // verify that the controller of the djinn cast that spell
                    && !stackObject.getManaCost().isEmpty()) { //handle cases like Ancestral Vision
                Spell spell = (Spell) stackObject;
                if (filter.match(stackObject, game)) {
                    ReplicateAbility replicateAbility = replicateAbilities.computeIfAbsent(spell.getId(), k -> new ReplicateAbility(spell.getSpellAbility().getManaCosts().getText()));
                    game.getState().addOtherAbility(spell.getCard(), replicateAbility, false); // Do not copy because paid and # of activations state is handled in the baility
                }
            }
        }
        if (game.getStack().isEmpty()) {
            replicateAbilities.clear();
        }
        return true;
    }
}
