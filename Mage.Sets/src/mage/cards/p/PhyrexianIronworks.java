package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianIronworks extends CardImpl {

    public PhyrexianIronworks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // Whenever you attack, you get {E}.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new GetEnergyCountersControllerEffect(1), 1));

        // {T}, Pay {E}{E}{E}: Create a 3/3 colorless Phyrexian Golem artifact creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new CreateTokenEffect(new PhyrexianGolemToken()), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        this.addAbility(ability);
    }

    private PhyrexianIronworks(final PhyrexianIronworks card) {
        super(card);
    }

    @Override
    public PhyrexianIronworks copy() {
        return new PhyrexianIronworks(this);
    }
}
