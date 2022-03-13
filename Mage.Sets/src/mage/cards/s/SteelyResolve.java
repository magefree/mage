
package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author markedagain
 */
public final class SteelyResolve extends CardImpl {

    public SteelyResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // As Steely Resolve enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.AddAbility)));
        // Creatures of the chosen type have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, new FilterSteelyResolve())));
    }

    private SteelyResolve(final SteelyResolve card) {
        super(card);
    }

    @Override
    public SteelyResolve copy() {
        return new SteelyResolve(this);
    }
}

class FilterSteelyResolve extends FilterCreaturePermanent {

    public FilterSteelyResolve() {
        super("All creatures of the chosen type");
    }

    public FilterSteelyResolve(final FilterSteelyResolve filter) {
        super(filter);
    }

    @Override
    public FilterSteelyResolve copy() {
        return new FilterSteelyResolve(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (super.match(permanent, playerId, source, game)) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subType != null && permanent.hasSubtype(subType, game)) {
                return true;
            }
        }
        return false;
    }

}
