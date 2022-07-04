package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivaanCultistOfTiamat extends CardImpl {

    public LivaanCultistOfTiamat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, target creature gets +X/+0 until end of turn, where X is that spell's mana value.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostTargetEffect(
                LivaanCultistOfTiamatValue.instance, StaticValue.get(0), Duration.EndOfTurn, true
        ), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private LivaanCultistOfTiamat(final LivaanCultistOfTiamat card) {
        super(card);
    }

    @Override
    public LivaanCultistOfTiamat copy() {
        return new LivaanCultistOfTiamat(this);
    }
}

enum LivaanCultistOfTiamatValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.ofNullable(effect.getValue("spellCast"))
                .map(Spell.class::cast)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public LivaanCultistOfTiamatValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "X";
    }
}
