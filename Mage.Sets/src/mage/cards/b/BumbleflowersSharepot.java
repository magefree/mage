package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BumbleflowersSharepot extends CardImpl {

    public BumbleflowersSharepot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Bumbleflower's Sharepot enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // {5}, {T}, Sacrifice Bumbleflower's Sharepot: Destroy target nonland permanent. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private BumbleflowersSharepot(final BumbleflowersSharepot card) {
        super(card);
    }

    @Override
    public BumbleflowersSharepot copy() {
        return new BumbleflowersSharepot(this);
    }
}
