package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfOneThousandCuts extends CardImpl {

    public WallOfOneThousandCuts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {W}: Wall of One Thousand Cuts can attack this turn as though it didn't have defender.
        this.addAbility(new SimpleActivatedAbility(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{W}")
        ));
    }

    private WallOfOneThousandCuts(final WallOfOneThousandCuts card) {
        super(card);
    }

    @Override
    public WallOfOneThousandCuts copy() {
        return new WallOfOneThousandCuts(this);
    }
}
