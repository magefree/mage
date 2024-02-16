package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadOsseosaur extends CardImpl {

    public DreadOsseosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setBlack(true);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Dread Osseosaur enters the battlefield or attacks, you may mill two cards.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new MillCardsControllerEffect(2), true
        ));
    }

    private DreadOsseosaur(final DreadOsseosaur card) {
        super(card);
    }

    @Override
    public DreadOsseosaur copy() {
        return new DreadOsseosaur(this);
    }
}
