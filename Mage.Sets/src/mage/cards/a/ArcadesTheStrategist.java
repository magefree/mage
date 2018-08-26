package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class ArcadesTheStrategist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public ArcadesTheStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature with defender enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));

        // Each creature you control with defender assigns combat damage equal to its toughness rather than its power and can attack as though it didn't have defender.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ArcadesTheStrategistCombatDamageRuleEffect());
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderAllEffect(
                Duration.WhileOnBattlefield, filter
        ).setText("and can attack as though it didn't have defender"));
        this.addAbility(ability);
    }

    public ArcadesTheStrategist(final ArcadesTheStrategist card) {
        super(card);
    }

    @Override
    public ArcadesTheStrategist copy() {
        return new ArcadesTheStrategist(this);
    }
}

class ArcadesTheStrategistCombatDamageRuleEffect extends ContinuousEffectImpl {

    public ArcadesTheStrategistCombatDamageRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each creature you control with defender assigns combat damage equal to its toughness rather than its power";
    }

    public ArcadesTheStrategistCombatDamageRuleEffect(final ArcadesTheStrategistCombatDamageRuleEffect effect) {
        super(effect);
    }

    @Override
    public ArcadesTheStrategistCombatDamageRuleEffect copy() {
        return new ArcadesTheStrategistCombatDamageRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Change the rule
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getControllerId()));
        filter.add(new AbilityPredicate(DefenderAbility.class));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
