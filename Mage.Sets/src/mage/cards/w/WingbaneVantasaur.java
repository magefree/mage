package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

public final class WingbaneVantasaur extends CardImpl {

    public WingbaneVantasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, choose one --
        // * Conjure a card named Savage Stomp into your hand.
        // * Conjure a card named Naturalize into your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConjureCardEffect("Savage Stomp"));
        ability.addMode(new Mode(new ConjureCardEffect("Naturalize")));
        this.addAbility(ability);
    }

    private WingbaneVantasaur(final WingbaneVantasaur card) {
        super(card);
    }

    @Override
    public WingbaneVantasaur copy() {
        return new WingbaneVantasaur(this);
    }
}
