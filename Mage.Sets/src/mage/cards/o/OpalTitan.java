package mage.cards.o;

import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author jeffwadsworth
 */
public final class OpalTitan extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public OpalTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // When an opponent casts a creature spell, if Opal Titan is an enchantment, Opal Titan becomes a 4/4 Giant creature with protection from each of that spell's colors.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new OpalTitanBecomesCreatureEffect(),
                filter, false, SetTargetPointer.SPELL);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT),
                "When an opponent casts a creature spell, if Opal Titan is an enchantment, Opal Titan becomes a 4/4 Giant creature with protection from each of that spell's colors."));

    }

    public OpalTitan(final OpalTitan card) {
        super(card);
    }

    @Override
    public OpalTitan copy() {
        return new OpalTitan(this);
    }
}

class OpalTitanBecomesCreatureEffect extends ContinuousEffectImpl implements SourceEffect {

    public OpalTitanBecomesCreatureEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "{this} becomes a 4/4 Giant creature with protection from each of that spell's colors.";
        this.addDependencyType(DependencyType.BecomeCreature);
    }

    public OpalTitanBecomesCreatureEffect(final OpalTitanBecomesCreatureEffect effect) {
        super(effect);
    }

    @Override
    public OpalTitanBecomesCreatureEffect copy() {
        return new OpalTitanBecomesCreatureEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
        Spell creatureSpellCast = game.getSpell(targetPointer.getFirst(game, source));
        if (creatureSpellCast != null
                && creatureSpellCast.getColor(game).hasColor()) {
            game.getState().setValue("opalTitanColor" + source.getSourceId(), creatureSpellCast.getColor(game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getCardType().clear();
                        permanent.addCardType(CardType.CREATURE);
                        permanent.getSubtype(game).add(SubType.GIANT);
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        if (((ObjectColor) game.getState().getValue("opalTitanColor" + source.getSourceId())) != null) {
                            for (ObjectColor color : ((ObjectColor) game.getState().getValue("opalTitanColor" + source.getSourceId())).getColors()) {
                                if (!permanent.getAbilities().contains(ProtectionAbility.from(color))) {
                                    permanent.addAbility(ProtectionAbility.from(color));
                                }
                            }
                        }
                    }
                    break;
                case PTChangingEffects_7:
                    if ((sublayer == SubLayer.CharacteristicDefining_7a)
                            || (sublayer == SubLayer.SetPT_7b)) {
                        permanent.getPower().setValue(4);
                        permanent.getToughness().setValue(4);
                    }
                    break;
            }
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.TypeChangingEffects_4;
    }

}
