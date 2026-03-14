package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfNightsReach extends CardImpl {

    public HondenOfNightsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, target opponent discards a card for each Shrine you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DiscardTargetEffect(ShrinesYouControlCount.FOR_EACH)).addHint(ShrinesYouControlCount.getHint());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HondenOfNightsReach(final HondenOfNightsReach card) {
        super(card);
    }

    @Override
    public HondenOfNightsReach copy() {
        return new HondenOfNightsReach(this);
    }

}
