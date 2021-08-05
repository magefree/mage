package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurtlandFrostpyre extends CardImpl {

    public SurtlandFrostpyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Surtland Frostpyre enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {2}{U}{U}{R}, {T}, Sacrifice Surtland Frostpyre: Scry 2. Surtland Frostpyre deals 2 damage to each creature. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ScryEffect(2).setText("Scry 2."),
                new ManaCostsImpl<>("{2}{U}{U}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private SurtlandFrostpyre(final SurtlandFrostpyre card) {
        super(card);
    }

    @Override
    public SurtlandFrostpyre copy() {
        return new SurtlandFrostpyre(this);
    }
}
