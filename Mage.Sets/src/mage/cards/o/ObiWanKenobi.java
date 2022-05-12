
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Duration;
import mage.game.command.emblems.ObiWanKenobiEmblem;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo import mage.game.command.emblems.ObiWanKenobiEmblem;
 */
public final class ObiWanKenobi extends CardImpl {

    public ObiWanKenobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OBI_WAN);

        this.setStartingLoyalty(5);

        // +1:Up to one target creature you control gains vigilance and protection from color of your choice until end of turn.
        Effect effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Up to one target creature you control gains vigilance");
        Ability ability = new LoyaltyAbility(effect, +1);
        effect = new GainProtectionFromColorTargetEffect(Duration.EndOfTurn);
        effect.setText("and protection from color of your choice until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -1:Tap up to three target creatures.
        ability = new LoyaltyAbility(new TapTargetEffect(), -1);
        ability.addTarget(new TargetCreaturePermanent(0, 3));
        this.addAbility(ability);

        // -7:You get emblem with "Creatures you control get +1/+1 and have vigilance, first strike, and lifelink."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ObiWanKenobiEmblem()), -7));

    }

    private ObiWanKenobi(final ObiWanKenobi card) {
        super(card);
    }

    @Override
    public ObiWanKenobi copy() {
        return new ObiWanKenobi(this);
    }
}
