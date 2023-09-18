package mage.cards.s;

import java.util.*;
import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author NinthWorld
 */
public final class SalvageTrader extends CardImpl {

    public SalvageTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CROLUTE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Exchange control of target artifact you control and target artifact an opponent controls with the same converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(Duration.EndOfGame,
                        "Exchange control of target artifact you control and target artifact an opponent controls with the same mana value", false, true),
                new TapSourceCost());
        FilterArtifactPermanent filterYou = new FilterArtifactPermanent("artifact you control");
        filterYou.add(TargetController.YOU.getControllerPredicate());
        ability.addTarget(new TargetArtifactPermanent(filterYou));
        FilterArtifactPermanent filterOpponent = new FilterArtifactPermanent("artifact an opponent controls with the same casting cost as your targeted artifact");
        filterOpponent.add(TargetController.OPPONENT.getControllerPredicate());
        filterOpponent.add(new SameCastingCostPredicate());
        ability.addTarget(new TargetArtifactPermanent(filterOpponent));

        this.addAbility(ability);
    }

    private SalvageTrader(final SalvageTrader card) {
        super(card);
    }

    @Override
    public SalvageTrader copy() {
        return new SalvageTrader(this);
    }
}

class SameCastingCostPredicate implements ObjectSourcePlayerPredicate<MageItem> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                    || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent firstTarget = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0));
            Permanent inputPermanent = game.getPermanent(input.getObject().getId());
            if (firstTarget != null && inputPermanent != null) {
                return firstTarget.getManaValue() == inputPermanent.getManaValue();
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Target with the same casting cost";
    }

}
