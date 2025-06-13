
package mage.cards.a;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author MTGfan
 */
public final class AnimateArtifact extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public AnimateArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its converted mana cost.
        this.addAbility(new SimpleStaticAbility(new AnimateArtifactContinuousEffect(Duration.WhileOnBattlefield)));
    }

    private AnimateArtifact(final AnimateArtifact card) {
        super(card);
    }

    @Override
    public AnimateArtifact copy() {
        return new AnimateArtifact(this);
    }
}

class AnimateArtifactContinuousEffect extends ContinuousEffectImpl {

    AnimateArtifactContinuousEffect(Duration duration) {
        super(duration, Outcome.Benefit);
        staticText = "As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its mana value";
    }

    private AnimateArtifactContinuousEffect(final AnimateArtifactContinuousEffect effect) {
        super(effect);
    }

    @Override
    public AnimateArtifactContinuousEffect copy() {
        return new AnimateArtifactContinuousEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addCardType(game, CardType.CREATURE);
                    break;
                case PTChangingEffects_7:
                    if (sublayer != SubLayer.SetPT_7b) {
                        continue;
                    }
                    permanent.getPower().setModifiedBaseValue(permanent.getManaValue());
                    permanent.getToughness().setModifiedBaseValue(permanent.getManaValue());
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null || permanent.isCreature(game)) {
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 ||
                layer == Layer.PTChangingEffects_7;
    }

}
