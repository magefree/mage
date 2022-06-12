

package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Loki
 */
public final class OriginSpellbomb extends CardImpl {

    public OriginSpellbomb (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new MyrToken(), 1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{W}")), false));
    }

    public OriginSpellbomb (final OriginSpellbomb card) {
        super(card);
    }

    @Override
    public OriginSpellbomb copy() {
        return new OriginSpellbomb(this);
    }

}
