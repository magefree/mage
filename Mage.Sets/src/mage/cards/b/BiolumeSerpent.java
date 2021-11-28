package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiolumeSerpent extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ISLAND, "Islands");

    public BiolumeSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.nightCard = true;

        // Sacrifice two Islands: Biolume Serpent can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private BiolumeSerpent(final BiolumeSerpent card) {
        super(card);
    }

    @Override
    public BiolumeSerpent copy() {
        return new BiolumeSerpent(this);
    }
}
