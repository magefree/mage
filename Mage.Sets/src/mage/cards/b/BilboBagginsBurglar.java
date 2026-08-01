package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BilboBagginsBurglar extends AdventureCard {

    public BilboBagginsBurglar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{U}", "Take a Glance", "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Bilbo Baggins enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Take a Glance
        // Scry 2.
        this.getSpellCard().getSpellAbility().addEffect(new ScryEffect(2));

        this.finalizeAdventure();
    }

    private BilboBagginsBurglar(final BilboBagginsBurglar card) {
        super(card);
    }

    @Override
    public BilboBagginsBurglar copy() {
        return new BilboBagginsBurglar(this);
    }
}
