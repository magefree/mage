package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class RayOfFrost extends CardImpl {

    public RayOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Ray of Frost enters the battlefield, if enchanted creature is red, tap it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()),
                RayOfFrostCondition.instance,
                "When {this} enters the battlefield, if enchanted creature is red, tap it."
        ));

        // As long as enchanted creature is red, it loses all abilities.
        this.addAbility(new SimpleStaticAbility(new RayOfFrostLoseAbilitiesEffect()));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    private RayOfFrost(final RayOfFrost card) {
        super(card);
    }

    @Override
    public RayOfFrost copy() {
        return new RayOfFrost(this);
    }
}

enum RayOfFrostCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        return creature != null && creature.getColor(game).isRed();
    }

    @Override
    public String toString() {
        return "if enchanted creature is red";
    }
}

class RayOfFrostLoseAbilitiesEffect extends ContinuousEffectImpl {

    public RayOfFrostLoseAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.staticText = "As long as enchanted creature is red, it loses all abilities";
    }

    private RayOfFrostLoseAbilitiesEffect(final RayOfFrostLoseAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public RayOfFrostLoseAbilitiesEffect copy() {
        return new RayOfFrostLoseAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null && creature.getColor(game).isRed()) {
                creature.removeAllAbilities(source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }
}
