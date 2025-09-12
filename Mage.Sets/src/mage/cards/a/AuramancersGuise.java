package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class AuramancersGuise extends CardImpl {

    public AuramancersGuise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 for each Aura attached to it and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                AuramancersGuiseValue.instance, AuramancersGuiseValue.instance, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);
    }

    private AuramancersGuise(final AuramancersGuise card) {
        super(card);
    }

    @Override
    public AuramancersGuise copy() {
        return new AuramancersGuise(this);
    }
}

enum AuramancersGuiseValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = Optional
                .ofNullable(sourceAbility.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        return permanent != null
                ? 2 * permanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.hasSubtype(SubType.AURA, game))
                .mapToInt(x -> 1)
                .sum()
                : 0;
    }

    @Override
    public AuramancersGuiseValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "for each Aura attached to it";
    }

    @Override
    public String toString() {
        return "2";
    }
}
