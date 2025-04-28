package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Grath
 */
public final class TevalArbiterOfVirtue extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("spells you cast");

    static {
        filter.add(Predicates.not(new AbilityPredicate(DelveAbility.class))); // So there are not redundant copies being added to each card
    }

    public TevalArbiterOfVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Spells you cast have delve.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new DelveAbility(false), filter)));

        // Whenever you cast a spell, you lose life equal to its mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(new LoseLifeSourceControllerEffect(TevalArbiterOfVirtueValue.instance).setText("you lose life equal to its mana value"), false));
    }

    private TevalArbiterOfVirtue(final TevalArbiterOfVirtue card) {
        super(card);
    }

    @Override
    public TevalArbiterOfVirtue copy() {
        return new TevalArbiterOfVirtue(this);
    }
}

enum TevalArbiterOfVirtueValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .filter(Objects::nonNull)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public TevalArbiterOfVirtueValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
