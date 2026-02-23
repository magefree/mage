package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author muz
 */
public final class RaidingSchemes extends CardImpl {

    public RaidingSchemes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{G}");

        // Each noncreature spell you cast has conspire.
        this.addAbility(new SimpleStaticAbility(new GainConspireEffect()));
    }

    private RaidingSchemes(final RaidingSchemes card) {
        super(card);
    }

    @Override
    public RaidingSchemes copy() {
        return new RaidingSchemes(this);
    }
}

class GainConspireEffect extends ContinuousEffectImpl {

    private final ConspireAbility conspireAbility;

    public GainConspireEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each noncreature spell you cast has conspire. <i>(As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)</i>";
        this.conspireAbility = new ConspireAbility(ConspireAbility.ConspireTargets.MORE);
    }

    private GainConspireEffect(final GainConspireEffect effect) {
        super(effect);
        this.conspireAbility = effect.conspireAbility;
    }

    @Override
    public GainConspireEffect copy() {
        return new GainConspireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if (!(stackObject instanceof Spell) || stackObject.isCopy()
                    || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Spell spell = (Spell) stackObject;
            if (StaticFilters.FILTER_SPELL_NON_CREATURE.match(stackObject, game)) {
                game.getState().addOtherAbility(spell.getCard(), conspireAbility.setAddedById(source.getSourceId()));
            }
        }
        return true;
    }
}
