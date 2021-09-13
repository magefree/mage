package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SolitaryHunter extends CardImpl {

    public SolitaryHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.o.OneOfThePack.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Solitary Hunter.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private SolitaryHunter(final SolitaryHunter card) {
        super(card);
    }

    @Override
    public SolitaryHunter copy() {
        return new SolitaryHunter(this);
    }
}