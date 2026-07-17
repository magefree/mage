package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class TheNeutrinos extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public TheNeutrinos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Alliance -- Whenever another creature you control enters, The Neutrinos get +1/+0 until end of turn.
        this.addAbility(new AllianceAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));

        // Whenever The Neutrinos attack, exile up to one target creature you own, then return it to the battlefield under your control tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(
                new ExileThenReturnTargetEffect(true, false, PutCards.BATTLEFIELD_TAPPED_ATTACKING)
                        .setText("exile up to one target creature you own, then return it "
                                + "to the battlefield under your control tapped and attacking"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private TheNeutrinos(final TheNeutrinos card) {
        super(card);
    }

    @Override
    public TheNeutrinos copy() {
        return new TheNeutrinos(this);
    }
}
