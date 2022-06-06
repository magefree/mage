
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.RevealSourceFromYourHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author Ketsuban
 */
public final class InfernalSpawnOfEvil extends CardImpl {

    public InfernalSpawnOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{6}{B}{B}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // 1B, Reveal Infernal Spawn of Evil from your hand, Say "It's coming!":
        // Infernal Spawn of Evil deals 1 damage to target opponent or planeswalker.
        // Activate this ability only during your upkeep and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.HAND, new DamageTargetEffect(1),
                new CompositeCost(
                    new ManaCostsImpl<>("{1}{B}"),
                    new CompositeCost(
                        new RevealSourceFromYourHandCost(),
                        new SayCost("It's coming!"),
                        "Reveal {this} from your hand, Say \"It's coming!\""),
                    "{1}{B}, Reveal {this} from your hand, Say \"It's coming!\""),
                1, new IsStepCondition(PhaseStep.UPKEEP, true));
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private InfernalSpawnOfEvil(final InfernalSpawnOfEvil card) {
        super(card);
    }

    @Override
    public InfernalSpawnOfEvil copy() {
        return new InfernalSpawnOfEvil(this);
    }
}

class SayCost extends CostImpl {

    private String message;

    public SayCost(String message) {
        this.message = message;
    }

    public SayCost(SayCost cost) {
        super(cost);
        this.message = cost.message;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        game.informPlayers(controller.getLogName() + ": " + message);
        return true;
    }

    @Override
    public Cost copy() {
        return new SayCost(this);
    }
}
