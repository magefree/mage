package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class CrowdOfTrueBelievers extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creature you control that's attacking alone");

    static {
        filter.add(AttackingAlonePredicate.instance);
    }

    public CrowdOfTrueBelievers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Target creature you control that's attacking alone gets +1/+0 until end of turn. You gain 1 life.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 0), new TapSourceCost());
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private CrowdOfTrueBelievers(final CrowdOfTrueBelievers card) {
        super(card);
    }

    @Override
    public CrowdOfTrueBelievers copy() {
        return new CrowdOfTrueBelievers(this);
    }
}

enum AttackingAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isAttacking()
            && game.getCombat().attacksAlone()
            && game.getCombat().getAttackers().contains(input.getId());
    }

    @Override
    public String toString() {
        return "Attacking alone";
    }
}
