package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PompousBattlemage extends PrepareCard {

    public PompousBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}", "Improvised Act", new CardType[]{CardType.SORCERY}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Improvised Act
        // Sorcery {R}
        // You may discard a card. If you do, draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ));
    }

    private PompousBattlemage(final PompousBattlemage card) {
        super(card);
    }

    @Override
    public PompousBattlemage copy() {
        return new PompousBattlemage(this);
    }
}
