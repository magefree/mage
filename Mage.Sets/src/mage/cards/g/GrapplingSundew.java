package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class GrapplingSundew extends CardImpl {

    public GrapplingSundew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {4}{G}: Grappling Sundew gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.EndOfTurn
                ), new ManaCostsImpl<>("{4}{G}")
        ));
    }

    private GrapplingSundew(final GrapplingSundew card) {
        super(card);
    }

    @Override
    public GrapplingSundew copy() {
        return new GrapplingSundew(this);
    }
}
