package mage.cards.p;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
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
public final class PistonFistCyclops extends CardImpl {

    public PistonFistCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as you've cast an instant or sorcery spell this turn, Piston-Fist Cyclops can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalAsThoughEffect(
                        new CanAttackAsThoughItDidntHaveDefenderSourceEffect(
                                Duration.WhileOnBattlefield
                        ), PistonFistCyclopsCondition.instance)
                        .setText("As long as you've cast an instant or sorcery spell this turn, "
                                + "{this} can attack as though it didn't have defender")
        ));
    }

    private PistonFistCyclops(final PistonFistCyclops card) {
        super(card);
    }

    @Override
    public PistonFistCyclops copy() {
        return new PistonFistCyclops(this);
    }
}

enum PistonFistCyclopsCondition implements Condition {
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
                    && spell.isInstantOrSorcery(game)) {
                return true;
            }
        }
        return false;
    }
}
