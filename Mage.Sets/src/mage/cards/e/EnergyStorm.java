package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class EnergyStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public EnergyStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(1)));

        // Prevent all damage that would be dealt by instant and sorcery spells.
        this.addAbility(new SimpleStaticAbility(new EnergyStormPreventionEffect()));

        // Creatures with flying don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private EnergyStorm(final EnergyStorm card) {
        super(card);
    }

    @Override
    public EnergyStorm copy() {
        return new EnergyStorm(this);
    }
}

class EnergyStormPreventionEffect extends PreventionEffectImpl {

    public EnergyStormPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "prevent all damage that would be dealt by instant and sorcery spells";
    }

    private EnergyStormPreventionEffect(final EnergyStormPreventionEffect effect) {
        super(effect);
    }

    @Override
    public EnergyStormPreventionEffect copy() {
        return new EnergyStormPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getSourceId());
        return spell != null && (spell.isInstant(game) || spell.isSorcery(game));
    }
}
