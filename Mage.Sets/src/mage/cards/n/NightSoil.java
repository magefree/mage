package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NightSoil extends CardImpl {

    public NightSoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // {1}, Exile two creature cards from a single graveyard: Create a 1/1 green Saproling creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new GenericManaCost(1));
        ability.addCost(new ExileFromGraveCost(new TargetCardInASingleGraveyard(2, 2, new FilterCreatureCard("creature cards"))));
        this.addAbility(ability);
    }

    private NightSoil(final NightSoil card) {
        super(card);
    }

    @Override
    public NightSoil copy() {
        return new NightSoil(this);
    }
}
