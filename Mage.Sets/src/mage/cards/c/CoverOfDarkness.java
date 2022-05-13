
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CoverOfDarkness extends CardImpl {

    public CoverOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // As Cover of Darkness enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.AddAbility)));
        // Creatures of the chosen type have fear.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FearAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCoverOfDarkness())));
    }

    private CoverOfDarkness(final CoverOfDarkness card) {
        super(card);
    }

    @Override
    public CoverOfDarkness copy() {
        return new CoverOfDarkness(this);
    }
}

class FilterCoverOfDarkness extends FilterCreaturePermanent {

    private SubType subType = null;

    public FilterCoverOfDarkness() {
        super("All creatures of the chosen type");
    }

    public FilterCoverOfDarkness(final FilterCoverOfDarkness filter) {
        super(filter);
        this.subType = filter.subType;
    }

    @Override
    public FilterCoverOfDarkness copy() {
        return new FilterCoverOfDarkness(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (super.match(permanent, playerId, source, game)) {
            if (subType == null) {
                subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
                if (subType == null) {
                    return false;
                }
            }
            if (permanent.hasSubtype(subType, game)) {
                return true;
            }
        }
        return false;
    }

}
