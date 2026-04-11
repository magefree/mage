package mage.cards.a;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AberrantManawurm extends CardImpl {

    public AberrantManawurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, this creature gets +X/+0 until end of turn, where X is the amount of mana spent to cast that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(
                AberrantManawurmValue.instance,
                StaticValue.get(0),
                Duration.EndOfTurn
            ),
            StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private AberrantManawurm(final AberrantManawurm card) {
        super(card);
    }

    @Override
    public AberrantManawurm copy() {
        return new AberrantManawurm(this);
    }
}

enum AberrantManawurmValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .map(spell -> ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game))
                .orElse(0);
    }

    @Override
    public AberrantManawurmValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the amount of mana spent to cast that spell";
    }

    @Override
    public String toString() {
        return "X";
    }
}
