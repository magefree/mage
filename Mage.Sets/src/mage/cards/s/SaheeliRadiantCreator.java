package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class SaheeliRadiantCreator extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Artificer or artifact spell");

    static {
        filter.add(Predicates.or(
                SubType.ARTIFICER.getPredicate(),
                CardType.ARTIFACT.getPredicate())
        );
    }

    public SaheeliRadiantCreator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an Artificer or artifact spell, you get {E}.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new GetEnergyCountersControllerEffect(1),
                filter, false);
        this.addAbility(ability);

        // At the beginning of combat on your turn, you may pay {E}{E}{E}. When you do, create a token that's a copy of target permanent you control, except it's a 5/5 artifact creature in addition to its other types and has haste. Sacrifice it at the beginning of the next end step.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
                new SaheeliRadiantCreatorCopyEffect(), false
        );
        reflexiveAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_A_PERMANENT));
        Ability combatTriggeredAbility = new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(reflexiveAbility, new PayEnergyCost(3),
                        "Pay {E}{E}{E}?")
        );
        this.addAbility(combatTriggeredAbility);
    }

    private SaheeliRadiantCreator(final SaheeliRadiantCreator card) {
        super(card);
    }

    @Override
    public SaheeliRadiantCreator copy() {
        return new SaheeliRadiantCreator(this);
    }
}

class SaheeliRadiantCreatorCopyEffect extends OneShotEffect {

    public SaheeliRadiantCreatorCopyEffect() {
        super(Outcome.Copy);
        staticText = "create a token that's a copy of target permanent you control, " +
                "except it's a 5/5 artifact creature in addition to its other types and has haste. " +
                "Sacrifice it at the beginning of the next end step.";
    }

    public SaheeliRadiantCreatorCopyEffect(final SaheeliRadiantCreatorCopyEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliRadiantCreatorCopyEffect copy() {
        return new SaheeliRadiantCreatorCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, CardType.CREATURE,
                true, 1, false, false, null,
                5, 5, false);
        effect.setBecomesArtifact(true);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
