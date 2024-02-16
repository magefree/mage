package mage.cards.m;

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
import mage.game.permanent.token.BearToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MotherBear extends CardImpl {

    public MotherBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{G}{G}, Exile Mother Bear from your graveyard: Create two 2/2 green Bear creature tokens. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new BearToken(), 2), new ManaCostsImpl<>("{3}{G}{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private MotherBear(final MotherBear card) {
        super(card);
    }

    @Override
    public MotherBear copy() {
        return new MotherBear(this);
    }
}
