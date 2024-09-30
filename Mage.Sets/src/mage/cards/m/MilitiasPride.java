package mage.cards.m;

import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.KithkinSoldierToken;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MilitiasPride extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public MilitiasPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.KITHKIN);

        // Whenever a nontoken creature you control attacks, you may pay {W}. If you do, create a 1/1 white Kithkin Soldier creature token that’s tapped and attacking.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new KithkinSoldierToken(), 1, true, true), new ManaCostsImpl<>("{W}")),
                false, filter
        ));

    }

    private MilitiasPride(final MilitiasPride card) {
        super(card);
    }

    @Override
    public MilitiasPride copy() {
        return new MilitiasPride(this);
    }
}
