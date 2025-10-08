package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyggWanderbrineShield extends CardImpl {

    private static final FilterCard filter = new FilterCard("each color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public SyggWanderbrineShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);
        this.nightCard = true;

        // Sygg can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever this creature transforms into Sygg, Wanderbrine Shield, target creature you control gains protection from each color until your next turn.
        Ability ability = new TransformIntoSourceTriggeredAbility(new GainAbilityTargetEffect(new ProtectionAbility(filter)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your first main phase, you may pay {U}. If you do, transform Sygg.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{U}"))
        ));
    }

    private SyggWanderbrineShield(final SyggWanderbrineShield card) {
        super(card);
    }

    @Override
    public SyggWanderbrineShield copy() {
        return new SyggWanderbrineShield(this);
    }
}
