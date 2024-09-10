package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CrystalBall extends CardImpl {

    public CrystalBall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(2), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        this.addAbility(ability);
    }

    private CrystalBall(final CrystalBall card) {
        super(card);
    }

    @Override
    public CrystalBall copy() {
        return new CrystalBall(this);
    }

}
