

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AlluringSiren extends CardImpl {

    public AlluringSiren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");

        this.subtype.add(SubType.SIREN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target creature an opponent controls attacks you this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn, TargetController.YOU), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private AlluringSiren(final AlluringSiren card) {
        super(card);
    }

    @Override
    public AlluringSiren copy() {
        return new AlluringSiren(this);
    }

}