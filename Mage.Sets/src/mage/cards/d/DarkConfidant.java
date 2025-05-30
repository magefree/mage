package mage.cards.d;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class DarkConfidant extends CardImpl {

    public DarkConfidant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RevealPutInHandLoseLifeEffect()));
    }

    private DarkConfidant(final DarkConfidant card) {
        super(card);
    }

    @Override
    public DarkConfidant copy() {
        return new DarkConfidant(this);
    }
}
