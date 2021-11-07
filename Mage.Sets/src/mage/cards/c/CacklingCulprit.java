package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CacklingCulprit extends CardImpl {

    public CacklingCulprit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.nightCard = true;

        // Whenever Cackling Culprit or another creature you control dies, you gain 1 life.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // {1}{B}: Cackling Culprit gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{B}")));
    }

    private CacklingCulprit(final CacklingCulprit card) {
        super(card);
    }

    @Override
    public CacklingCulprit copy() {
        return new CacklingCulprit(this);
    }
}
