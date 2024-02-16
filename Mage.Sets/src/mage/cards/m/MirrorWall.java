
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MirrorWall extends CardImpl {

    public MirrorWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {W}: Mirror Wall can attack this turn as though it didn't have defender.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}")));
    }

    private MirrorWall(final MirrorWall card) {
        super(card);
    }

    @Override
    public MirrorWall copy() {
        return new MirrorWall(this);
    }
}
