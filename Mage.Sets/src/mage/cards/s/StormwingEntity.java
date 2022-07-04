package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormwingEntity extends CardImpl {

    private static final ConditionHint hint = new ConditionHint(
            StormwingEntityCondition.instance, "You cast an instant or sorcery spell this turn"
    );

    public StormwingEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {2}{U} less to cast if you've cast an instant or sorcery spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(
                new ManaCostsImpl<>("{2}{U}"), StormwingEntityCondition.instance
        )).setRuleAtTheTop(true).addHint(hint), new SpellsCastWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Stormwing Entity enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));
    }

    private StormwingEntity(final StormwingEntity card) {
        super(card);
    }

    @Override
    public StormwingEntity copy() {
        return new StormwingEntity(this);
    }
}

enum StormwingEntityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
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

    @Override
    public String toString() {
        return "you've cast an instant or sorcery spell this turn";
    }
}
