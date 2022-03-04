package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GatstafShepherd extends CardImpl {

    public GatstafShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = GatstafHowler.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Gatstaf Shepherd.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private GatstafShepherd(final GatstafShepherd card) {
        super(card);
    }

    @Override
    public GatstafShepherd copy() {
        return new GatstafShepherd(this);
    }
}
