package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NemesisPhoenix extends CardImpl {

    public NemesisPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{R}: Return Nemesis Phoenix from your graveyard to the battlefield tapped and attacking. Activate only during the declare attackers step and only if you're attacking two or more opponents.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnToBattlefieldUnderOwnerControlSourceEffect(true, true, -1)
                        .setText("return {this} from your graveyard to the battlefield tapped and attacking"),
                new ManaCostsImpl<>("{2}{R}"), NemesisPhoenixCondition.instance
        ));
    }

    private NemesisPhoenix(final NemesisPhoenix card) {
        super(card);
    }

    @Override
    public NemesisPhoenix copy() {
        return new NemesisPhoenix(this);
    }
}

enum NemesisPhoenixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStep().getType() != PhaseStep.DECLARE_ATTACKERS) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        return game
                .getCombat()
                .getGroups()
                .stream()
                .filter(combatGroup -> combatGroup
                        .getAttackers()
                        .stream()
                        .map(game::getControllerId)
                        .anyMatch(source::isControlledBy))
                .map(CombatGroup::getDefenderId)
                .distinct()
                .filter(opponents::contains)
                .count() >= 2;
    }

    @Override
    public String toString() {
        return "during the declare attackers step and only if you're attacking two or more opponents";
    }
}
