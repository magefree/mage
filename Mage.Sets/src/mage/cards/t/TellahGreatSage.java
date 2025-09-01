package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.HeroToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TellahGreatSage extends CardImpl {

    public TellahGreatSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, create a 1/1 colorless Hero creature token. If four or more mana was spent to cast that spell, draw two cards. If eight or more mana was spent to cast that spell, sacrifice Tellah and it deals that much damage to each opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HeroToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2), TellahGreatSageCondition.FOUR,
                "If four or more mana was spent to cast that spell, draw two cards"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), TellahGreatSageCondition.EIGHT,
                "If eight or more mana was spent to cast that spell, " +
                        "sacrifice {this} and it deals that much damage to each opponent"
        ).addEffect(new DamagePlayersEffect(TellahGreatSageValue.instance, TargetController.OPPONENT)));
        this.addAbility(ability);
    }

    private TellahGreatSage(final TellahGreatSage card) {
        super(card);
    }

    @Override
    public TellahGreatSage copy() {
        return new TellahGreatSage(this);
    }
}

enum TellahGreatSageCondition implements Condition {
    FOUR(4),
    EIGHT(8);
    private final int amount;

    TellahGreatSageCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getStackAbility)
                .map(Ability::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .orElse(0) >= amount;
    }
}

enum TellahGreatSageValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .map(Spell::getStackAbility)
                .map(Ability::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .orElse(0);
    }

    @Override
    public TellahGreatSageValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
