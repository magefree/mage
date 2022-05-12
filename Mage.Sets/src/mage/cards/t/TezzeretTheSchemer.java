
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.command.emblems.TezzeretTheSchemerEmblem;
import mage.game.permanent.token.EtheriumCellToken;
import mage.target.common.TargetCreaturePermanent;
/**
 * @author JRHerlehy
 */
public final class TezzeretTheSchemer extends CardImpl {

    public TezzeretTheSchemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);

        //Starting Loyalty - 5
        this.setStartingLoyalty(5);

        // +1: Create a colorless artifact token named Etherium Cell which has "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new EtheriumCellToken()), 1));

        // -2: Target creature gets +X/-X until end of turn, where X is the number of artifacts you control.
        DynamicValue count = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent("artifacts you control"));
        Effect effect = new BoostTargetEffect(count, new SignInversionDynamicValue(count), Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/-X until end of turn, where X is the number of artifacts you control");
        Ability ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -7: You get an emblem with "At the beginning of combat on your turn, target artifact you control becomes an artifact creature with base power and toughness 5/5."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TezzeretTheSchemerEmblem()), -7));
    }

    private TezzeretTheSchemer(final TezzeretTheSchemer card) {
        super(card);
    }

    @Override
    public TezzeretTheSchemer copy() {
        return new TezzeretTheSchemer(this);
    }
}

