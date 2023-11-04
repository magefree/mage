package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfCivilization extends CardImpl {

    public TempleOfCivilization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Ojer Taq, Deepest Foundation.)

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {2}{W}, {T}: Transform Temple of Civilization. Activate only if you attacked with three or more creatures this turn and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new TransformSourceEffect(),
                new ManaCostsImpl("{2}{W}"),
                TempleOfCivilizationCondition.instance,
                TimingRule.SORCERY
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private TempleOfCivilization(final TempleOfCivilization card) {
        super(card);
    }

    @Override
    public TempleOfCivilization copy() {
        return new TempleOfCivilization(this);
    }
}

enum TempleOfCivilizationCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerAttackedWatcher watcher = game.getState().getWatcher(PlayerAttackedWatcher.class);
        return watcher != null && watcher.getNumberOfAttackersCurrentTurn(source.getControllerId()) >= 3;
    }

    @Override
    public String toString() {
        return "you attacked with three or more creatures this turn";
    }
}
