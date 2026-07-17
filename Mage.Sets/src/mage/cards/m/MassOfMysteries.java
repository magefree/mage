package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MassOfMysteries extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ELEMENTAL, "another target Elemental you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MassOfMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, another target Elemental you control gains Myriad until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            new GainAbilityTargetEffect(new MyriadAbility())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MassOfMysteries(final MassOfMysteries card) {
        super(card);
    }

    @Override
    public MassOfMysteries copy() {
        return new MassOfMysteries(this);
    }
}
