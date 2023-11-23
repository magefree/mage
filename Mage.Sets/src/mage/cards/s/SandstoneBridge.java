package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SandstoneBridge extends CardImpl {

    public SandstoneBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Sandstone Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Sandstone Bridge enters the battlefield, target creature gets +1/+1 and gains vigilance until end of turn.
        Effect effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("target creature gets +1/+1"), false);
        ability.addEffect(effect.setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private SandstoneBridge(final SandstoneBridge card) {
        super(card);
    }

    @Override
    public SandstoneBridge copy() {
        return new SandstoneBridge(this);
    }
}
