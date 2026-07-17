package mage.cards.p;

import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author muz
 */
public final class PotionersTrove extends CardImpl {

    public PotionersTrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: You gain 2 life. Activate only if you've cast an instant or sorcery spell this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new GainLifeEffect(2),
            new TapSourceCost(),
            PotionersTroveCondition.instance
        );
        this.addAbility(ability);
    }

    private PotionersTrove(final PotionersTrove card) {
        super(card);
    }

    @Override
    public PotionersTrove copy() {
        return new PotionersTrove(this);
    }
}

enum PotionersTroveCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null
                && watcher.getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> spell.isInstantOrSorcery(game));
    }

    @Override
    public String toString() {
        return "you've cast an instant or sorcery spell this turn";
    }
}
