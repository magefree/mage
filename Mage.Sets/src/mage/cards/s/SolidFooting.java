package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolidFooting extends CardImpl {

    public SolidFooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // As long as enchanted creature has vigilance, it assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new GauntletsOfLightEffect()));
    }

    private SolidFooting(final SolidFooting card) {
        super(card);
    }

    @Override
    public SolidFooting copy() {
        return new SolidFooting(this);
    }
}

class GauntletsOfLightEffect extends ContinuousEffectImpl {

    GauntletsOfLightEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as enchanted creature has vigilance, " +
                "it assigns combat damage equal to its toughness rather than its power";
    }

    private GauntletsOfLightEffect(final GauntletsOfLightEffect effect) {
        super(effect);
    }

    @Override
    public GauntletsOfLightEffect copy() {
        return new GauntletsOfLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || permanent.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
        if (attachedTo == null || !attachedTo.getAbilities().containsKey(VigilanceAbility.getInstance().getId())) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentIdPredicate(permanent.getAttachedTo()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
