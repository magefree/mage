package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class ElasIlKorSadisticPilgrim extends CardImpl {

    public ElasIlKorSadisticPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));

        // Whenever another creature you control dies, each opponent loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
                .setTriggerPhrase("Whenever another creature you control dies, "));
    }

    private ElasIlKorSadisticPilgrim(final ElasIlKorSadisticPilgrim card) {
        super(card);
    }

    @Override
    public ElasIlKorSadisticPilgrim copy() {
        return new ElasIlKorSadisticPilgrim(this);
    }
}
