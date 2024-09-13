package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuskmournsDomination extends CardImpl {

    public DuskmournsDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        this.addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));

        // Enchanted creature gets -3/-0 and loses all abilities.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(-3, 0));
        ability.addEffect(new DuskmournsDominationEffect());
        this.addAbility(ability);
    }

    private DuskmournsDomination(final DuskmournsDomination card) {
        super(card);
    }

    @Override
    public DuskmournsDomination copy() {
        return new DuskmournsDomination(this);
    }
}

class DuskmournsDominationEffect extends ContinuousEffectImpl {

    DuskmournsDominationEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "and loses all abilities";
    }

    private DuskmournsDominationEffect(final DuskmournsDominationEffect effect) {
        super(effect);
    }

    @Override
    public DuskmournsDominationEffect copy() {
        return new DuskmournsDominationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.removeAllAbilities(source.getSourceId(), game));
        return true;
    }
}
