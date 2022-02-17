
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LoathsomeCatoblepas extends CardImpl {

    public LoathsomeCatoblepas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}: Loathsome Catoblepas must be blocked this turn if able.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(), new ManaCostsImpl("{2}{G}")));
        // When Loathsome Catoblepas dies, target creature an opponent controls gets -3/-3 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-3,-3, Duration.EndOfTurn), false);
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private LoathsomeCatoblepas(final LoathsomeCatoblepas card) {
        super(card);
    }

    @Override
    public LoathsomeCatoblepas copy() {
        return new LoathsomeCatoblepas(this);
    }
}
