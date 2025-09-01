package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CascadeSeer extends CardImpl {
    public CascadeSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Cascade Seer enters the battlefield, scry X, where X is the number of creatures in your party.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new ScryEffect(PartyCount.instance)
                                .setText("scry X, where X is the number of creatures in your party")
                ).addHint(PartyCountHint.instance));
    }


    private CascadeSeer(final CascadeSeer card) {
        super(card);
    }

    @Override
    public CascadeSeer copy() {
        return new CascadeSeer(this);
    }
}