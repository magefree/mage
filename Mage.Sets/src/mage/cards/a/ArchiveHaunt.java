package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchiveHaunt extends CardImpl {

    public ArchiveHaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Archive Haunt attacks, draw a card, then discard a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // If Archive Haunt would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(DisturbAbility.makeBackAbility());
    }

    private ArchiveHaunt(final ArchiveHaunt card) {
        super(card);
    }

    @Override
    public ArchiveHaunt copy() {
        return new ArchiveHaunt(this);
    }
}
