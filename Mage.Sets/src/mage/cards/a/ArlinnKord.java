package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ArlinnKord extends CardImpl {

    public ArlinnKord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);

        this.secondSideCardClazz = mage.cards.a.ArlinnEmbracedByTheMoon.class;

        this.setStartingLoyalty(3);

        // +1: Until end of turn, up to one target creature gets +2/+2 and gains vigilance and haste.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("until end of turn, up to one target creature gets +2/+2"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // 0: Create a 2/2 green Wolf creature token. Transform Arlinn Kord.
        this.addAbility(new TransformAbility());
        ability = new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0);
        ability.addEffect(new TransformSourceEffect());
        this.addAbility(ability);
    }

    private ArlinnKord(final ArlinnKord card) {
        super(card);
    }

    @Override
    public ArlinnKord copy() {
        return new ArlinnKord(this);
    }
}
