package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author TheElk801
 */
public final class Leapfrog extends CardImpl {

    public Leapfrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Leapfrog has flying as long as you've cast an instant or sorcery spell this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), LeapfrogCondition.instance,
                        "{this} has flying as long as you've cast "
                        + "an instant or sorcery spell this turn."
                )
        ), new SpellsCastWatcher());
    }

    public Leapfrog(final Leapfrog card) {
        super(card);
    }

    @Override
    public Leapfrog copy() {
        return new Leapfrog(this);
    }
}

enum LeapfrogCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher
                = game.getState().getWatcher(
                        SpellsCastWatcher.class
                );
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        if (spells == null) {
            return false;
        }
        for (Spell spell : spells) {
            if (!spell.getSourceId().equals(source.getSourceId())
                    && spell.isInstantOrSorcery()) {
                return true;
            }
        }
        return false;
    }
}
