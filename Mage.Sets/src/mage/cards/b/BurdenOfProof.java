package mage.cards.b;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurdenOfProof extends CardImpl {

    private static final Condition condition = new InvertCondition(
            new AttachedToMatchesFilterCondition(new FilterControlledPermanent(SubType.DETECTIVE))
    );
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DETECTIVE, "");

    public BurdenOfProof(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 as long as it's a Detective you control. Otherwise, it has base power and toughness 1/1 and can't block Detectives.
        Ability ability = new SimpleStaticAbility(new BurdenOfProofEffect());
        ability.addEffect(new ConditionalRestrictionEffect(new CantBlockAttachedEffect(
                AttachmentType.AURA, Duration.WhileOnBattlefield, filter
        ), condition, "and can't block Detectives"));
        this.addAbility(ability);
    }

    private BurdenOfProof(final BurdenOfProof card) {
        super(card);
    }

    @Override
    public BurdenOfProof copy() {
        return new BurdenOfProof(this);
    }
}

class BurdenOfProofEffect extends ContinuousEffectImpl {

    BurdenOfProofEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "enchanted creature gets +2/+2 as long as it's a Detective " +
                "you control. Otherwise, it has base power and toughness 1/1";
    }

    private BurdenOfProofEffect(final BurdenOfProofEffect effect) {
        super(effect);
    }

    @Override
    public BurdenOfProofEffect copy() {
        return new BurdenOfProofEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            if (permanent.isControlledBy(source.getControllerId()) && permanent.hasSubtype(SubType.DETECTIVE, game)) {
                if (sublayer == SubLayer.ModifyPT_7c) {
                    permanent.getPower().increaseBoostedValue(2);
                    permanent.getToughness().increaseBoostedValue(2);
                }
            } else if (sublayer == SubLayer.SetPT_7b) {
                permanent.getPower().setModifiedBaseValue(1);
                permanent.getToughness().setModifiedBaseValue(1);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer != Layer.PTChangingEffects_7) {
            return false;
        }
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7;
    }
}
