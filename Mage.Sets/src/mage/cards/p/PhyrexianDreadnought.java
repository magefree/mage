
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PhyrexianDreadnought extends CardImpl {

    public PhyrexianDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DREADNOUGHT);

        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Phyrexian Dreadnought enters the battlefield, sacrifice it unless you sacrifice any number of creatures with total power 12 or greater.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new PhyrexianDreadnoughtSacrificeCost())));

    }

    private PhyrexianDreadnought(final PhyrexianDreadnought card) {
        super(card);
    }

    @Override
    public PhyrexianDreadnought copy() {
        return new PhyrexianDreadnought(this);
    }
}

class PhyrexianDreadnoughtSacrificeCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of creatures with total power 12 or greater");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PhyrexianDreadnoughtSacrificeCost() {
        this.addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true));
        this.text = "sacrifice any number of creatures with total power 12 or greater";
    }

    public PhyrexianDreadnoughtSacrificeCost(final PhyrexianDreadnoughtSacrificeCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        int sumPower = 0;
        if (targets.choose(Outcome.Sacrifice, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.sacrifice(source, game)) {
                    sumPower += permanent.getPower().getValue();
                }
            }
        }
        game.informPlayers("Sacrificed creatures with total power of " + sumPower);
        paid = sumPower >= 12;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game)) {
            if (!permanent.getId().equals(source.getSourceId())) {
                sumPower += permanent.getPower().getValue();
            }
        }
        return sumPower >= 12;
    }

    @Override
    public PhyrexianDreadnoughtSacrificeCost copy() {
        return new PhyrexianDreadnoughtSacrificeCost(this);
    }
}
