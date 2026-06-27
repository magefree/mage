package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.ruleModifying.CantCastDuringYourTurnEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class JenniferWalters extends ModalDoubleFacedCard {

    public JenniferWalters(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            // Jennifer Walters
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ADVISOR, SubType.HERO}, "{1}{W}",
            "The Sensational She-Hulk",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GAMMA, SubType.HERO}, "{3}{G}{W}{W}"
        );

        // 1.
        // Jennifer Walters
        // Legendary Creature — Human Advisor Hero
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(3));

        // Your opponents can't cast spells during your turn.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CantCastDuringYourTurnEffect()));

        // {3}{G}{W}{W}: Transform Jennifer Walters. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{G}{W}{W}")));

        // 2.
        // The Sensational She-Hulk
        // Legendary Creature — Gamma Hero
        this.getRightHalfCard().setPT(new MageInt(6), new MageInt(6));

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control is dealt damage, you may have The Sensational She-Hulk deal that much damage to any target. Do this only once each turn.
        Ability ability = new DealtDamageAnyTriggeredAbility(
            new DamageTargetEffect(SavedDamageValue.MUCH)
                .setText("have {this} deal that much damage to any target"),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE, SetTargetPointer.PERMANENT, true
        ).setDoOnlyOnceEachTurn(true);
        ability.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability);
    }

    private JenniferWalters(final JenniferWalters card) {
        super(card);
    }

    @Override
    public JenniferWalters copy() {
        return new JenniferWalters(this);
    }
}
