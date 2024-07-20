package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.TargetObjectMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzureBeastbinder extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or greater");
    private static final FilterPermanent filter2
            = new FilterPermanent("artifact, creature, or planeswalker an opponent controls");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition = new TargetObjectMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public AzureBeastbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Azure Beastbinder can't be blocked by creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Azure Beastbinder attacks, up to one target artifact, creature, or planeswalker an opponent controls loses all abilities until your next turn. If it's a creature, it also has base power and toughness 2/2 until your next turn.
        Ability ability = new AttacksTriggeredAbility(new LoseAllAbilitiesTargetEffect(Duration.UntilYourNextTurn));
        ability.addEffect(new ConditionalOneShotEffect(new AddContinuousEffectToGame(
                new SetBasePowerToughnessTargetEffect(2, 2, Duration.UntilYourNextTurn)
        ), condition, "if it's a creature, it also has base power and toughness 2/2 until your next turn"));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        this.addAbility(ability);
    }

    private AzureBeastbinder(final AzureBeastbinder card) {
        super(card);
    }

    @Override
    public AzureBeastbinder copy() {
        return new AzureBeastbinder(this);
    }
}
