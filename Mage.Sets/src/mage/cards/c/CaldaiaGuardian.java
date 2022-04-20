package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaldaiaGuardian extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you control with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public CaldaiaGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Caldaia Guardian or another creature you control with mana value 4 or greater dies, create two 1/1 green and white Citizen creature tokens.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new CitizenGreenWhiteToken(), 2), false, filter
        ));

        // Blitz {2}{G}
        this.addAbility(new BlitzAbility("{2}{G}"));
    }

    private CaldaiaGuardian(final CaldaiaGuardian card) {
        super(card);
    }

    @Override
    public CaldaiaGuardian copy() {
        return new CaldaiaGuardian(this);
    }
}
