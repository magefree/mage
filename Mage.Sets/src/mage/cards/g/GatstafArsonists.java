package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GatstafArsonists extends CardImpl {

    public GatstafArsonists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.g.GatstafRavagers.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Gatstaf Arsonists.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private GatstafArsonists(final GatstafArsonists card) {
        super(card);
    }

    @Override
    public GatstafArsonists copy() {
        return new GatstafArsonists(this);
    }
}
