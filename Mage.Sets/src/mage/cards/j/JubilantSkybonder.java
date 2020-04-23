package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JubilantSkybonder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public JubilantSkybonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control with flying have "Spells your opponents cast that target this creature cost {2} more to cast."
        ContinuousEffect effect = new GainAbilityAllEffect(
                new SimpleStaticAbility(new JubilantSkybonderEffect()),
                Duration.WhileOnBattlefield, filter
        ).withForceQuotes();
        effect.setDependedToType(DependencyType.AddingAbility);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private JubilantSkybonder(final JubilantSkybonder card) {
        super(card);
    }

    @Override
    public JubilantSkybonder copy() {
        return new JubilantSkybonder(this);
    }
}

class JubilantSkybonderEffect extends CostModificationEffectImpl {

    JubilantSkybonderEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target this creature cost {2} more to cast";
    }

    private JubilantSkybonderEffect(JubilantSkybonderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
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
                .map(abilityToModify.getModes()::get)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(source.getSourceId()::equals);
    }

    @Override
    public JubilantSkybonderEffect copy() {
        return new JubilantSkybonderEffect(this);
    }
}
