
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author TheElk801
 */
public final class RelicRunner extends CardImpl {

    public RelicRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Relic Runner can't be blocked if you've cast an historic spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(new CantBeBlockedSourceAbility(), Duration.WhileOnBattlefield),
                        new CastHistoricSpellThisTurnCondition(),
                        "{this} can't be blocked if you've cast a historic spell this turn. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"
                )
        ));
    }

    private RelicRunner(final RelicRunner card) {
        super(card);
    }

    @Override
    public RelicRunner copy() {
        return new RelicRunner(this);
    }
}

class CastHistoricSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spells != null) {
                for (Spell spell : spells) {
                    if (!spell.getSourceId().equals(source.getSourceId()) && spell.isHistoric(game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
