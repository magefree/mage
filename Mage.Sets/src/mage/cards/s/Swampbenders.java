package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Swampbenders extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterPermanent(SubType.SWAMP, "Swamps on the battlefield")
    );

    public Swampbenders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Swampbenders's power and toughness are each equal to the number of Swamps on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));

        // Lands you control are Swamps in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new SwampbendersEffect()));
    }

    private Swampbenders(final Swampbenders card) {
        super(card);
    }

    @Override
    public Swampbenders copy() {
        return new Swampbenders(this);
    }
}

class SwampbendersEffect extends ContinuousEffectImpl {

    SwampbendersEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AIDontUseIt);
        staticText = "lands you control are Swamps in addition to their other types.";
        this.dependendToTypes.add(DependencyType.BecomeNonbasicLand);
        this.dependencyTypes.add(DependencyType.BecomeSwamp);
    }

    private SwampbendersEffect(final SwampbendersEffect effect) {
        super(effect);
    }

    @Override
    public SwampbendersEffect copy() {
        return new SwampbendersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability ability = new BlackManaAbility();
        for (Permanent land : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_LAND, source.getControllerId(), game
        )) {
            // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
            // So the ability removing has to be done before Layer 6
            // Lands have their mana ability intrinsically, so that is added in layer 4
            land.addSubType(game, SubType.SWAMP);
            if (!land.getAbilities().containsRule(ability)) {
                land.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}
