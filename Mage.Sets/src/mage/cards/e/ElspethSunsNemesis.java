package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethSunsNemesis extends CardImpl {

    public ElspethSunsNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);
        this.setStartingLoyalty(5);

        // −1: Up to two target creatures you control each get +2/+1 until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(2, 1)
                .setText("up to two target creatures you control each get +2/+1 until end of turn"), -1);
        ability.addTarget(new TargetControlledCreaturePermanent(0, 2));
        this.addAbility(ability);

        // −2: Create two 1/1 white Human Soldier creature tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new HumanSoldierToken(), 2), -2));

        // −3: You gain 5 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(5), -3));

        // Escape—{4}{W}{W}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{4}{W}{W}", 4));
    }

    private ElspethSunsNemesis(final ElspethSunsNemesis card) {
        super(card);
    }

    @Override
    public ElspethSunsNemesis copy() {
        return new ElspethSunsNemesis(this);
    }
}
