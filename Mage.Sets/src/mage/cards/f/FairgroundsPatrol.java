package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FairgroundsPatrol extends CardImpl {

    public FairgroundsPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{W}, Exile Fairgrounds Patrol from your graveyard: Create a 1/1 colorless Thopter creature token with flying. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new ThopterColorlessToken()), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private FairgroundsPatrol(final FairgroundsPatrol card) {
        super(card);
    }

    @Override
    public FairgroundsPatrol copy() {
        return new FairgroundsPatrol(this);
    }
}
