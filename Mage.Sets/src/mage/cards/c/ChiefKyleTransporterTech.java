package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 * @author muz
 */
public final class ChiefKyleTransporterTech extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChiefKyleTransporterTech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, exile up to one other target creature you control.
        // Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new LandfallAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private ChiefKyleTransporterTech(final ChiefKyleTransporterTech card) {
        super(card);
    }

    @Override
    public ChiefKyleTransporterTech copy() {
        return new ChiefKyleTransporterTech(this);
    }
}
