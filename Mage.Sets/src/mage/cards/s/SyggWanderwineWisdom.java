package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyggWanderwineWisdom extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("each color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public SyggWanderwineWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.WIZARD}, "{1}{U}",
                "Sygg, Wanderbrine Shield",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.ROGUE}, "W"
        );

        // Sygg, Wanderwine Wisdom
        this.getLeftHalfCard().setPT(2, 2);

        // Sygg can't be blocked.
        this.getLeftHalfCard().addAbility(new CantBeBlockedSourceAbility());

        // Whenever this creature enters or transforms into Sygg, Wanderwine Wisdom, target creature gains "Whenever this creature deals combat damage to a player or planeswalker, draw a card" until end of turn.
        Ability ability = new TransformsOrEntersTriggeredAbility(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your first main phase, you may pay {W}. If you do, transform Sygg.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{W}"))
        ));

        // Sygg, Wanderbrine Shield
        this.getRightHalfCard().setPT(2, 2);

        // Sygg can't be blocked.
        this.getRightHalfCard().addAbility(new CantBeBlockedSourceAbility());

        // Whenever this creature transforms into Sygg, Wanderbrine Shield, target creature you control gains protection from each color until your next turn.
        Ability ability2 = new TransformIntoSourceTriggeredAbility(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.UntilYourNextTurn));
        ability2.addTarget(new TargetControlledCreaturePermanent());
        this.getRightHalfCard().addAbility(ability2);

        // At the beginning of your first main phase, you may pay {U}. If you do, transform Sygg.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{U}"))
        ));
    }

    private SyggWanderwineWisdom(final SyggWanderwineWisdom card) {
        super(card);
    }

    @Override
    public SyggWanderwineWisdom copy() {
        return new SyggWanderwineWisdom(this);
    }
}
