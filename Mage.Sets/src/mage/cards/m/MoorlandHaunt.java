
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class MoorlandHaunt extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card from your graveyard");

    public MoorlandHaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {W}{U}, {tap}, Exile a creature card from your graveyard: Create a 1/1 white Spirit creature token with flying.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new SpiritWhiteToken()),
                new ManaCostsImpl<>("{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);
    }

    private MoorlandHaunt(final MoorlandHaunt card) {
        super(card);
    }

    @Override
    public MoorlandHaunt copy() {
        return new MoorlandHaunt(this);
    }
}
