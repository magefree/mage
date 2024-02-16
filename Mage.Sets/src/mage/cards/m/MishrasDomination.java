package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasDomination extends CardImpl {

    public MishrasDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // As long as you control enchanted creature, it gets +2/+2. Otherwise, it can't block.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2), MishrasDominationCondition.TRUE,
                "as long as you control enchanted creature, it gets +2/+2"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                MishrasDominationCondition.FALSE, "otherwise, it can't block"
        ));
        this.addAbility(ability);
    }

    private MishrasDomination(final MishrasDomination card) {
        super(card);
    }

    @Override
    public MishrasDomination copy() {
        return new MishrasDomination(this);
    }
}

enum MishrasDominationCondition implements Condition {
    TRUE(true),
    FALSE(false);
    private final boolean value;

    MishrasDominationCondition(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .map(source::isControlledBy)
                .orElse(false)
                .equals(value);
    }
}
