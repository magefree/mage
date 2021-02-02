package mage.cards.a;

import java.util.UUID;

import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ReachAbility;
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
 * @author noahg
 */
public final class AetherWeb extends CardImpl {

    public AetherWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1, has reach, and can block creatures with shadow as though they didn't have shadow.
        StaticAbility staticAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1)
                .setText("Enchanted creature gets +1/+1, has reach, and can block creatures with shadow as though they didn't have shadow."));
        staticAbility.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.AURA).setText(""));
        staticAbility.addEffect(new AetherWebEffect());
        this.addAbility(staticAbility);
    }

    private AetherWeb(final AetherWeb card) {
        super(card);
    }

    @Override
    public AetherWeb copy() {
        return new AetherWeb(this);
    }
}

class AetherWebEffect extends AsThoughEffectImpl {

    public AetherWebEffect() {
        super(AsThoughEffectType.BLOCK_SHADOW, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "";
    }

    public AetherWebEffect(final AetherWebEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AetherWebEffect copy() {
        return new AetherWebEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null && sourceId.equals(sourcePermanent.getAttachedTo());
    }
}