
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class Chronosavant extends CardImpl {

    public Chronosavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}{W}: Return Chronosavant from your graveyard to the battlefield tapped. You skip your next turn.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false), new ManaCostsImpl<>("{1}{W}"));
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(ability);
    }

    private Chronosavant(final Chronosavant card) {
        super(card);
    }

    @Override
    public Chronosavant copy() {
        return new Chronosavant(this);
    }
}
