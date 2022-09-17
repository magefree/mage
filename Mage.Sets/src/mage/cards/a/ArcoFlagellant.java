package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcoFlagellant extends CardImpl {

    public ArcoFlagellant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Arco-Flagellant can't block.
        this.addAbility(new CantBlockAbility());

        // Endurant -- Pay 3 life: Arco-Flagellant gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new PayLifeCost(3)).withFlavorWord("Endurant"));
    }

    private ArcoFlagellant(final ArcoFlagellant card) {
        super(card);
    }

    @Override
    public ArcoFlagellant copy() {
        return new ArcoFlagellant(this);
    }
}
