
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ZulaportCutthroat extends CardImpl {


    public ZulaportCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE, SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zulaport Cutthroat or another creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private ZulaportCutthroat(final ZulaportCutthroat card) {
        super(card);
    }

    @Override
    public ZulaportCutthroat copy() {
        return new ZulaportCutthroat(this);
    }
}
