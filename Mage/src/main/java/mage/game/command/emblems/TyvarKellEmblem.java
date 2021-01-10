package mage.game.command.emblems;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class TyvarKellEmblem extends Emblem {

    private static final FilterSpell filter = new FilterSpell("an Elf spell");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    // −6: You get an emblem with "Whenever you cast an Elf spell, it gains haste until end of turn and you draw two cards."
    public TyvarKellEmblem() {
        this.setName("Emblem Tyvar");
        this.setExpansionSetCodeForImage("KHM");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, new TyvarKellEmblemEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn), filter, false, true
        );
        ability.addEffect(new DrawCardSourceControllerEffect(2).setText("and you draw two cards"));
        this.getAbilities().add(ability);
    }
}

class TyvarKellEmblemEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public TyvarKellEmblemEffect(Ability ability, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.generateGainAbilityDependencies(ability, null);
        this.staticText = "it gains haste until end of turn";
    }

    public TyvarKellEmblemEffect(final TyvarKellEmblemEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public TyvarKellEmblemEffect copy() {
        return new TyvarKellEmblemEffect(this);
    }

    @Override
    public void init (Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            if (spell != null) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    affectedObjectList.add(new MageObjectReference(card, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                UUID sourceId = it.next().getSourceId();
                Card card = game.getCard(sourceId);
                Permanent perm = game.getPermanent(sourceId);
                if (card != null && !card.hasAbility(ability, game)) {
                    game.getState().addOtherAbility(card, ability);
                }
                if (perm != null) {
                    perm.addAbility(ability, source.getSourceId(), game);
                }
                if (card == null && perm == null) {
                    it.remove();
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
            return true;
        }
        return false;
    }
}
