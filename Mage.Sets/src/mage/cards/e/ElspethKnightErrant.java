
package mage.cards.e;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.command.emblems.ElspethKnightErrantEmblem;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ElspethKnightErrant extends CardImpl {

    public ElspethKnightErrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);

        this.setStartingLoyalty(4);

        // +1: Create a 1/1 white Soldier creature token.
        Token token = new SoldierToken();
        token.setOriginalExpansionSetCode("ALA"); // to get the right image
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(token), 1));

        // +1: Target creature gets +3/+3 and gains flying until end of turn.
        Effects effects1 = new Effects();
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+3");
        effects1.add(effect);
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        effects1.add(effect);
        LoyaltyAbility ability1 = new LoyaltyAbility(effects1, 1);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // -8: You get an emblem with "Artifacts, creatures, enchantments, and lands you control are indestructible."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ElspethKnightErrantEmblem()), -8));
    }

    private ElspethKnightErrant(final ElspethKnightErrant card) {
        super(card);
    }

    @Override
    public ElspethKnightErrant copy() {
        return new ElspethKnightErrant(this);
    }

}
