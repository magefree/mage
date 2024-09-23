package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import java.util.UUID;

public final class PlaguecraftersFamiliar extends CardImpl {

    public PlaguecraftersFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Plaguecrafter’s Familiar enters the battlefield, choose a creature card in your hand. It perpetually gains deathtouch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ChooseACardInYourHandItPerpetuallyGainsEffect(DeathtouchAbility.getInstance(), new FilterCreatureCard())));
    }

    private PlaguecraftersFamiliar(final PlaguecraftersFamiliar card) {
        super(card);
    }

    @Override
    public PlaguecraftersFamiliar copy() {
        return new PlaguecraftersFamiliar(this);
    }
}
