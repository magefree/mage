
package mage.cards.b;

import mage.MageInt;
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
    private int zoneChangeCounter;
    private UUID permanentId;

    BloodlordOfVaasgothEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains bloodthirst 3";
    }

    private BloodlordOfVaasgothEffect(final BloodlordOfVaasgothEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.permanentId = effect.permanentId;
    }

    @Override
    public BloodlordOfVaasgothEffect copy() {
        return new BloodlordOfVaasgothEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (object != null) {
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            permanentId = object.getSourceId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(ability, source.getSourceId(), game);
        } else {
            if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
                discard();
            }
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            if (spell != null) { // Bloodthirst checked while spell is on the stack so needed to give it already to the spell
                game.getState().addOtherAbility(spell.getCard(), ability, true);
            }
        }
        return true;
    }

}
