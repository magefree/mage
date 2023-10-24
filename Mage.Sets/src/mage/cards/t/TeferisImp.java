package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SourcePhaseInTriggeredAbility;
import mage.abilities.common.SourcePhaseOutTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TeferisImp extends CardImpl {

    public TeferisImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // Whenever Teferi's Imp phases out, discard a card.
        this.addAbility(new SourcePhaseOutTriggeredAbility(
                new DiscardControllerEffect(1), false)
        );

        // Whenever Teferi's Imp phases in, draw a card.
        this.addAbility(new SourcePhaseInTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false)
        );
    }

    private TeferisImp(final TeferisImp card) {
        super(card);
    }

    @Override
    public TeferisImp copy() {
        return new TeferisImp(this);
    }
}
