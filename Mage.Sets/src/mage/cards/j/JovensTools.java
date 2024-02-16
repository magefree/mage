package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class JovensTools extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Walls");
    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public JovensTools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {4}, {T}: Target creature can't be blocked this turn except by Walls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfTurn), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JovensTools(final JovensTools card) {
        super(card);
    }

    @Override
    public JovensTools copy() {
        return new JovensTools(this);
    }
}
