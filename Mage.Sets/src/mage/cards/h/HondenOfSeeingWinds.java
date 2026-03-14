package mage.cards.h;

import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfSeeingWinds extends CardImpl {

    public HondenOfSeeingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, draw a card for each Shrine you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(ShrinesYouControlCount.FOR_EACH)).addHint(ShrinesYouControlCount.getHint()));
    }

    private HondenOfSeeingWinds(final HondenOfSeeingWinds card) {
        super(card);
    }

    @Override
    public HondenOfSeeingWinds copy() {
        return new HondenOfSeeingWinds(this);
    }

}
