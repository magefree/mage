package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergenceZone extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public EmergenceZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Emergence Zone: You may cast spells this turn as thought they had flash.
        Ability ability = new SimpleActivatedAbility(
                new CastAsThoughItHadFlashAllEffect(
                        Duration.EndOfTurn, filter
                ), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EmergenceZone(final EmergenceZone card) {
        super(card);
    }

    @Override
    public EmergenceZone copy() {
        return new EmergenceZone(this);
    }
}
