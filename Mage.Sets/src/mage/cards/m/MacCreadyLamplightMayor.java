package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 * @author Cguy7777
 */
public final class MacCreadyLamplightMayor extends CardImpl {

    private static final FilterControlledCreaturePermanent filterTwoOrLess
            = new FilterControlledCreaturePermanent("creature you control with power 2 or less");
    private static final FilterCreaturePermanent filterFourOrGreater
            = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filterTwoOrLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filterFourOrGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public MacCreadyLamplightMayor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a creature you control with power 2 or less attacks, it gains skulk until end of turn.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new GainAbilityTargetEffect(
                        new SkulkAbility(),
                        Duration.EndOfTurn,
                        "it gains skulk until end of turn. <i>(It can't be blocked by creatures with greater power.)</i>"),
                false,
                filterTwoOrLess,
                true));

        // Whenever a creature with power 4 or greater attacks you, its controller loses 2 life and you gain 2 life.
        Ability ability = new AttackedByCreatureTriggeredAbility(
                Zone.BATTLEFIELD,
                new LoseLifeTargetEffect(2).setText("its controller loses 2 life"),
                false,
                SetTargetPointer.PLAYER,
                filterFourOrGreater);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private MacCreadyLamplightMayor(final MacCreadyLamplightMayor card) {
        super(card);
    }

    @Override
    public MacCreadyLamplightMayor copy() {
        return new MacCreadyLamplightMayor(this);
    }
}
