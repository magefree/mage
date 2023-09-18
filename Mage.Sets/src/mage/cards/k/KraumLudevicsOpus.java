package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class KraumLudevicsOpus extends CardImpl {

    public KraumLudevicsOpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever an opponent casts their second spell each turn, draw a card.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.OPPONENT
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KraumLudevicsOpus(final KraumLudevicsOpus card) {
        super(card);
    }

    @Override
    public KraumLudevicsOpus copy() {
        return new KraumLudevicsOpus(this);
    }
}
