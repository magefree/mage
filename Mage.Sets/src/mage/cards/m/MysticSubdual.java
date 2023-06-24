package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
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
 * @author TheElk801
 */
public final class MysticSubdual extends CardImpl {

    public MysticSubdual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -2/-0 and loses all abilities.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(-2, 0));
        ability.addEffect(new MysticSubdualEffect());
        this.addAbility(ability);
    }

    private MysticSubdual(final MysticSubdual card) {
        super(card);
    }

    @Override
    public MysticSubdual copy() {
        return new MysticSubdual(this);
    }
}

class MysticSubdualEffect extends ContinuousEffectImpl {

    MysticSubdualEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "and loses all abilities";
    }

    private MysticSubdualEffect(final MysticSubdualEffect effect) {
        super(effect);
    }

    @Override
    public MysticSubdualEffect copy() {
        return new MysticSubdualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return true;
        }
        Permanent creature = game.getPermanent(permanent.getAttachedTo());
        if (creature == null) {
            return true;
        }
        creature.removeAllAbilities(source.getSourceId(), game);
        return true;
    }
}
