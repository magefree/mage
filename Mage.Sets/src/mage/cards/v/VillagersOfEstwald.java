package mage.cards.v;

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
public final class VillagersOfEstwald extends CardImpl {

    public VillagersOfEstwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.h.HowlpackOfEstwald.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Villagers of Estwald.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private VillagersOfEstwald(final VillagersOfEstwald card) {
        super(card);
    }

    @Override
    public VillagersOfEstwald copy() {
        return new VillagersOfEstwald(this);
    }
}
