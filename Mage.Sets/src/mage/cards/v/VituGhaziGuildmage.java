

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class VituGhaziGuildmage extends CardImpl {

    public VituGhaziGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SHAMAN);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{G}{W}: Create a 3/3 green Centaur creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new mage.game.permanent.token.CentaurToken()), new ManaCostsImpl<>("{4}{G}{W}")));

        // {2}{G}{W}: Populate. (Create a token that's a copy of a creature token you control.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PopulateEffect(), new ManaCostsImpl<>("{2}{G}{W}")));
    }

    private VituGhaziGuildmage(final VituGhaziGuildmage card) {
        super(card);
    }

    @Override
    public VituGhaziGuildmage copy() {
        return new VituGhaziGuildmage(this);
    }
}