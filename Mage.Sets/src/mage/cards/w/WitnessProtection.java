package mage.cards.w;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitnessProtection extends CardImpl {

    public WitnessProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature loses all abilities and is a green and white Citizen creature with base power and toughness 1/1 named Legitimate Businessperson.
        this.addAbility(new SimpleStaticAbility(new WitnessProtectionEffect()));
    }

    private WitnessProtection(final WitnessProtection card) {
        super(card);
    }

    @Override
    public WitnessProtection copy() {
        return new WitnessProtection(this);
    }
}

class WitnessProtectionEffect extends ContinuousEffectImpl {

    private static final ObjectColor color = new ObjectColor("GW");

    WitnessProtectionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "enchanted creature loses all abilities and is a green and white Citizen creature " +
                "with base power and toughness 1/1 named Legitimate Businessperson";
    }

    private WitnessProtectionEffect(final WitnessProtectionEffect effect) {
        super(effect);
    }

    @Override
    public WitnessProtectionEffect copy() {
        return new WitnessProtectionEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TextChangingEffects_3:
                permanent.setName("Legitimate Businessperson");
                return true;
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.CITIZEN);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setColor(color);
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(1);
                    permanent.getToughness().setModifiedBaseValue(1);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TextChangingEffects_3:
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
