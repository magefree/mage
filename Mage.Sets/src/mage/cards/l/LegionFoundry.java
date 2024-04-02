package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GolemToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LegionFoundry extends CardImpl {

    public LegionFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When Legion Foundry enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice another artifact: Create a 3/3 colorless Golem artifact creature token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new GolemToken()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_SHORT_TEXT));
        this.addAbility(ability);
    }

    private LegionFoundry(final LegionFoundry card) {
        super(card);
    }

    @Override
    public LegionFoundry copy() {
        return new LegionFoundry(this);
    }
}
