package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LarderZombie extends CardImpl {

    public LarderZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Tap three untapped creatures you control: Look at the top card of your library. You may put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new SurveilEffect(1),
                new TapTargetCost(new TargetControlledPermanent(
                        3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        ));
    }

    private LarderZombie(final LarderZombie card) {
        super(card);
    }

    @Override
    public LarderZombie copy() {
        return new LarderZombie(this);
    }
}
