package mage.cards.e;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EatenByPiranhas extends CardImpl {

    public EatenByPiranhas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and is a black Skeleton creature with base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new EatenByPiranhasEffect()));
    }

    private EatenByPiranhas(final EatenByPiranhas card) {
        super(card);
    }

    @Override
    public EatenByPiranhas copy() {
        return new EatenByPiranhas(this);
    }
}

/**
 * Inspired by {@link mage.cards.w.WitnessProtection}
 */
class EatenByPiranhasEffect extends ContinuousEffectImpl {

    EatenByPiranhasEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "enchanted creature loses all abilities and is a black Skeleton creature " +
                "with base power and toughness 1/1";
    }

    private EatenByPiranhasEffect(final EatenByPiranhasEffect effect) {
        super(effect);
    }

    @Override
    public EatenByPiranhasEffect copy() {
        return new EatenByPiranhasEffect(this);
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
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.SKELETON);
                break;
            case ColorChangingEffects_5:
                permanent.getColor(game).setColor(ObjectColor.BLACK);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(1);
                    permanent.getToughness().setModifiedBaseValue(1);
                }
                break;
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
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
