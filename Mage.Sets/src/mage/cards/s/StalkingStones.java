
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Loki
 */
public final class StalkingStones extends CardImpl {

    public StalkingStones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new StalkingStonesToken(), CardType.LAND, Duration.WhileOnBattlefield)
                .setText("{this} becomes a 3/3 Elemental artifact creature that's still a land"), new GenericManaCost(6)));
    }

    private StalkingStones(final StalkingStones card) {
        super(card);
    }

    @Override
    public StalkingStones copy() {
        return new StalkingStones(this);
    }
}

class StalkingStonesToken extends TokenImpl {

    public StalkingStonesToken() {
        super("Elemental", "3/3 Elemental artifact creature");
        this.cardType.add(CardType.CREATURE);
        this.cardType.add(CardType.ARTIFACT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    public StalkingStonesToken(final StalkingStonesToken token) {
        super(token);
    }

    public StalkingStonesToken copy() {
        return new StalkingStonesToken(this);
    }
}
