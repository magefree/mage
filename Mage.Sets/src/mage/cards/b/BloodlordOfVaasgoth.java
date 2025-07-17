
package mage.cards.b;

import mage.MageInt;
import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public final class BloodlordOfVaasgoth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Vampire creature spell");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public BloodlordOfVaasgoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodthirst 3
        this.addAbility(new BloodthirstAbility(3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Vampire creature spell, it gains bloodthirst 3.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BloodlordOfVaasgothEffect(), filter, false, SetTargetPointer.SPELL));
    }

    private BloodlordOfVaasgoth(final BloodlordOfVaasgoth card) {
        super(card);
    }

    @Override
    public BloodlordOfVaasgoth copy() {
        return new BloodlordOfVaasgoth(this);
    }
}

class BloodlordOfVaasgothEffect extends ContinuousEffectImpl {

    private Ability ability = new BloodthirstAbility(3);

    BloodlordOfVaasgothEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains bloodthirst 3";
    }

    private BloodlordOfVaasgothEffect(final BloodlordOfVaasgothEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public BloodlordOfVaasgothEffect copy() {
        return new BloodlordOfVaasgothEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (object != null) {
            int zcc = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            affectedObjectList.add(new MageObjectReference(object.getSourceId(), zcc, game));
        }
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            if (object instanceof Spell) {
                game.getState().addOtherAbility(((Spell) object).getCard(), ability, true);
            } else {
                ((Permanent) object).addAbility(ability, source.getSourceId(), game);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) { // Bloodthirst checked while spell is on the stack so needed to give it already to the spell
            affectedObjects.add(spell);
        } else {
            for (MageObjectReference mor : affectedObjectList) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    affectedObjects.add(permanent);
                }
            }
        }
        if (affectedObjects.isEmpty()) {
            this.discard();
            return false;
        }
        return true;
    }

}
