package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class WorfStillNotMerry extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking non-Faerie creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(SubType.FAERIE.getPredicate()));
    }

    public WorfStillNotMerry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Worf attacks, for each attacking non-Faerie creature, create a tapped and attacking 1/1 blue Faerie creature token with flying.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
            new FaerieToken(), new PermanentsOnBattlefieldCount(filter), true, true
        ).setText("for each attacking non-Faerie creature, create a tapped and attacking "
            + "1/1 blue Faerie creature token with flying")));
    }

    private WorfStillNotMerry(final WorfStillNotMerry card) {
        super(card);
    }

    @Override
    public WorfStillNotMerry copy() {
        return new WorfStillNotMerry(this);
    }
}
