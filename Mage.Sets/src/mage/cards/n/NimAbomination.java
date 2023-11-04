package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class NimAbomination extends CardImpl {

    public NimAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if Nim Abomination is untapped, you lose 3 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(3),
                TargetController.YOU, SourceTappedCondition.UNTAPPED, false));
    }

    private NimAbomination(final NimAbomination card) {
        super(card);
    }

    @Override
    public NimAbomination copy() {
        return new NimAbomination(this);
    }
}
