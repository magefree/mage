
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MartyredRusalka extends CardImpl {

    public MartyredRusalka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, Sacrifice a creature: Target creature can't attack this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantAttackTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private MartyredRusalka(final MartyredRusalka card) {
        super(card);
    }

    @Override
    public MartyredRusalka copy() {
        return new MartyredRusalka(this);
    }
}
