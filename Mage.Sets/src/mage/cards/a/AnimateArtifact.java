
package mage.cards.a;

import java.util.UUID;
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnimateArtifactContinuousEffect(Duration.WhileOnBattlefield)));
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

    public AnimateArtifactContinuousEffect(Duration duration) {
        super(duration, Outcome.Benefit);
        staticText = "As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its mana value";
    }

    public AnimateArtifactContinuousEffect(final AnimateArtifactContinuousEffect effect) {
        super(effect);
    }

    @Override
    public AnimateArtifactContinuousEffect copy() {
        return new AnimateArtifactContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Not sure, if this is layerwise handled absolutely correctly
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null && !permanent.isCreature(game)) {
                if (sublayer == SubLayer.NA) {
                    permanent.addCardType(game, CardType.CREATURE);
                    permanent.getPower().setValue(permanent.getManaValue());
                    permanent.getToughness().setValue(permanent.getManaValue());
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

}
