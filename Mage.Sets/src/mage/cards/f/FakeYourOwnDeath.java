package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class FakeYourOwnDeath extends CardImpl {

    public FakeYourOwnDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield tapped under its owner's control and you create a Treasure token."
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0).setText("Until end of turn, target creature gets +2/+0"));
        Ability gainedAbility = new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true));
        gainedAbility.addEffect(new CreateTokenEffect(new TreasureToken()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(gainedAbility)
                .setText("and gains \"When this creature dies, return it to the battlefield tapped under its owner's control and you create a Treasure token.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FakeYourOwnDeath(final FakeYourOwnDeath card) {
        super(card);
    }

    @Override
    public FakeYourOwnDeath copy() {
        return new FakeYourOwnDeath(this);
    }
}
