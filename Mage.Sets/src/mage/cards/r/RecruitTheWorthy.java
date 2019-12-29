package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecruitTheWorthy extends CardImpl {

    public RecruitTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Create a 1/1 white Soldier creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierToken()));
    }

    private RecruitTheWorthy(final RecruitTheWorthy card) {
        super(card);
    }

    @Override
    public RecruitTheWorthy copy() {
        return new RecruitTheWorthy(this);
    }
}
