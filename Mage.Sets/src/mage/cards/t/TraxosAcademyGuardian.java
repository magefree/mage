package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TraxosAcademyGuardian extends CardImpl {

    private static final ConditionHint hint = new ConditionHint(
            StormwingEntityCondition.instance, "You cast a noncreature spell this turn"
    );

    public TraxosAcademyGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // This spell costs {2} less to cast if you've cast a noncreature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(
            new ManaCostsImpl<>("{2}"), StormwingEntityCondition.instance
        )).setRuleAtTheTop(true).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private TraxosAcademyGuardian(final TraxosAcademyGuardian card) {
        super(card);
    }

    @Override
    public TraxosAcademyGuardian copy() {
        return new TraxosAcademyGuardian(this);
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
                    && !spell.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}
