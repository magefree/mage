package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallapheBelovedOfTheSea extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creatures and enchantments");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public CallapheBelovedOfTheSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Callaphe's power is equal to your to devotion to blue.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetPowerSourceEffect(DevotionCount.U, Duration.EndOfGame)
                .setText("{this}'s power is equal to your devotion to blue")
        ).addHint(DevotionCount.U.getHint()));

        // Creatures and enchantments you control have "Spells your opponents cast that target this permanent cost {1} more to cast".
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new SimpleStaticAbility(
                        new CallapheBelovedOfTheSeaEffect()
                ), Duration.WhileOnBattlefield, filter
        )));
    }

    private CallapheBelovedOfTheSea(final CallapheBelovedOfTheSea card) {
        super(card);
    }

    @Override
    public CallapheBelovedOfTheSea copy() {
        return new CallapheBelovedOfTheSea(this);
    }
}

class CallapheBelovedOfTheSeaEffect extends CostModificationEffectImpl {

    CallapheBelovedOfTheSeaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target this permanent cost {1} more to cast";
    }

    private CallapheBelovedOfTheSeaEffect(CallapheBelovedOfTheSeaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        return abilityToModify
                .getModes()
                .getSelectedModes()
                .stream()
                .map(uuid -> abilityToModify.getModes().get(uuid))
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(uuid -> uuid.equals(source.getSourceId()));
    }

    @Override
    public CallapheBelovedOfTheSeaEffect copy() {
        return new CallapheBelovedOfTheSeaEffect(this);
    }
}
