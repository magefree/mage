package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StopCold extends CardImpl {

    public StopCold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Stop Cold enters the battlefield, tap enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted permanent loses all abilities and doesn't untap during its controller's untap step.
        Ability ability = new SimpleStaticAbility(new StopColdEffect());
        ability.addEffect(new DontUntapInControllersUntapStepEnchantedEffect()
                .setText("and doesn't untap during its controller's untap step"));
        this.addAbility(ability);
    }

    private StopCold(final StopCold card) {
        super(card);
    }

    @Override
    public StopCold copy() {
        return new StopCold(this);
    }
}

class StopColdEffect extends ContinuousEffectImpl {

    StopColdEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "enchanted permanent loses all abilities";
    }

    private StopColdEffect(final StopColdEffect effect) {
        super(effect);
    }

    @Override
    public StopColdEffect copy() {
        return new StopColdEffect(this);
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
