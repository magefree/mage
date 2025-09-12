package mage.cards.s;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SledgeClassSeedship extends CardImpl {

    public SledgeClassSeedship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Whenever this Spacecraft attacks, you may put a creature from your hand onto the battlefield.
        // 4/5
        this.addAbility(new StationLevelAbility(7)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new AttacksTriggeredAbility(
                        new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A)
                ))
                .withPT(4, 5));
    }

    private SledgeClassSeedship(final SledgeClassSeedship card) {
        super(card);
    }

    @Override
    public SledgeClassSeedship copy() {
        return new SledgeClassSeedship(this);
    }
}
