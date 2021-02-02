package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class WindingCanyons extends CardImpl {

    public WindingCanyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {tap}: Until end of turn, you may cast creature spells as though they had flash.
        Effect effect = new AddContinuousEffectToGame(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_CARD_CREATURE));
        effect.setText("Until end of turn, you may cast creature spells as though they had flash.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WindingCanyons(final WindingCanyons card) {
        super(card);
    }

    @Override
    public WindingCanyons copy() {
        return new WindingCanyons(this);
    }
}
