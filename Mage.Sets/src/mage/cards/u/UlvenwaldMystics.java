package mage.cards.u;

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
public final class UlvenwaldMystics extends CardImpl {

    public UlvenwaldMystics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.u.UlvenwaldPrimordials.class;

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Ulvenwald Mystics.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private UlvenwaldMystics(final UlvenwaldMystics card) {
        super(card);
    }

    @Override
    public UlvenwaldMystics copy() {
        return new UlvenwaldMystics(this);
    }
}
