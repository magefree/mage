package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfTheDead extends CardImpl {

    public TempleOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Aclazotz, Deepest Betrayal.)

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{B}, {T}: Transform Temple of the Dead. Activate only if a player has one or fewer cards in hand and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new TransformSourceEffect(),
                new ManaCostsImpl("{2}{B}"),
                TempleOfTheDeadCondition.instance,
                TimingRule.SORCERY
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TempleOfTheDead(final TempleOfTheDead card) {
        super(card);
    }

    @Override
    public TempleOfTheDead copy() {
        return new TempleOfTheDead(this);
    }
}

enum TempleOfTheDeadCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        return player
                .getInRange()
                .stream()
                .map(game::getPlayer)
                .anyMatch(p -> p != null && p.getHand().size() <= 1);
    }

    @Override
    public String toString() {
        return "if a player has one or fewer cards in hand";
    }
}
