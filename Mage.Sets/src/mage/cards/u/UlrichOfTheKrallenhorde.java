package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlrichOfTheKrallenhorde extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("non-Werewolf creature you don't control");

    static {
        filter.add(Predicates.not(SubType.WEREWOLF.getPredicate()));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public UlrichOfTheKrallenhorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}{G}",
                "Ulrich, Uncontested Alpha",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "RG"
        );

        // Ulrich of the Krallenhorde
        this.getLeftHalfCard().setPT(4, 4);

        // Whenever this creature enters the battlefield or transforms into Ulrich of the Krallenhorde, target creature gets +4/+4 until end of turn.
        Ability ability = new TransformsOrEntersTriggeredAbility(
                new BoostTargetEffect(4, 4), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Ulrich of the Krallenhorde.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Ulrich, Uncontested Alpha
        this.getRightHalfCard().setPT(6, 6);

        // Whenever this creature transforms into Ulrich, Uncontested Alpha, you may have it fight target non-Werewolf creature you don't control.
        Ability ability2 = new TransformIntoSourceTriggeredAbility(
                new FightTargetSourceEffect()
                        .setText("you may have it fight target non-Werewolf creature you don't control"),
                true, true
        );
        ability2.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability2);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ulrich, Uncontested Alpha.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private UlrichOfTheKrallenhorde(final UlrichOfTheKrallenhorde card) {
        super(card);
    }

    @Override
    public UlrichOfTheKrallenhorde copy() {
        return new UlrichOfTheKrallenhorde(this);
    }
}
