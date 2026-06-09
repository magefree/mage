package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishraClaimedByGix extends MeldCard {

    private static final DynamicValue xValue = new AttackingCreatureCount();
    private static final Condition condition = new MeldCondition(
            "Phyrexian Dragon Engine", CardType.CREATURE, true
    );
    private static final FilterPermanent filter = new FilterPermanent("artifact or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public MishraClaimedByGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.HUMAN, SubType.ARTIFICER}, "{2}{B}{R}",
                "Mishra, Lost to Phyrexia",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ARTIFICER}, "BR");

        // Mishra, Claimed by Gix
        this.getLeftHalfCard().setPT(3, 5);

        this.meldsWithClazz = mage.cards.p.PhyrexianDragonEngine.class;

        // Whenever you attack, each opponent loses X life and you gain X life, where X is the number of attacking creatures. If Mishra, Claimed by Gix and a creature named Phyrexian Dragon Engine are attacking, and you both own and control them, exile them, then meld them into Mishra, Lost to Phyrexia. It enters the battlefield tapped and attacking.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new LoseLifeOpponentsEffect(xValue).setText("each opponent loses X life"), 1
        );
        ability.addEffect(new GainLifeEffect(xValue)
                .setText("and you gain X life, where X is the number of attacking creatures."));
        ability.addEffect(new ConditionalOneShotEffect(
                new MeldEffect("Phyrexian Dragon Engine", "Mishra, Lost to Phyrexia", true),
                condition, "If {this} and a creature named Phyrexian Dragon Engine are attacking, " +
                "and you both own and control them, exile them, then meld them into Mishra, Lost to Phyrexia. " +
                "It enters the battlefield tapped and attacking"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Mishra, Lost to Phyrexia
        this.getRightHalfCard().setPT(9, 9);

        // Whenever Mishra, Lost to Phyrexia enters the battlefield or attacks, choose three --
        // * Target opponent discards two cards.
        ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        ability.getModes().setMinModes(3);
        ability.getModes().setMaxModes(3);

        // * Mishra deals 3 damage to any target.
        ability.addMode(new Mode(new DamageTargetEffect(3)).addTarget(new TargetAnyTarget()));

        // * Destroy target artifact or planeswalker.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));

        // * Creatures you control gain menace and trample until end of turn.
        ability.addMode(new Mode(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("creatures you control gain menace")).addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and trample until end of turn")));

        // * Creatures you don't control get -1/-1 until end of turn.
        ability.addMode(new Mode(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false
        )));

        // * Create two tapped Powerstone tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new PowerstoneToken(), 2, true, false)));

        this.getRightHalfCard().addAbility(ability);
    }

    private MishraClaimedByGix(final MishraClaimedByGix card) {
        super(card);
    }

    @Override
    public MishraClaimedByGix copy() {
        return new MishraClaimedByGix(this);
    }
}
