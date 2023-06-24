package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessEnchantedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
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
 * @author TheElk801
 */
public final class StasisField extends CardImpl {

    public StasisField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has base power and toughness 0/2, has defender, and loses all other abilities.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessEnchantedEffect(0, 2));
        ability.addEffect(new StasisFieldEffect());
        this.addAbility(ability);
    }

    private StasisField(final StasisField card) {
        super(card);
    }

    @Override
    public StasisField copy() {
        return new StasisField(this);
    }
}

class StasisFieldEffect extends ContinuousEffectImpl {

    StasisFieldEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = ", has defender, and loses all other abilities";
    }

    private StasisFieldEffect(final StasisFieldEffect effect) {
        super(effect);
    }

    @Override
    public StasisFieldEffect copy() {
        return new StasisFieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        permanent.removeAllAbilities(source.getSourceId(), game);
        permanent.addAbility(DefenderAbility.getInstance(), source.getSourceId(), game);
        return true;
    }
}
