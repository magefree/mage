package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GeierReachBandit extends CardImpl {

    public GeierReachBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.v.VildinPackAlpha.class;

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Geier Reach Bandit.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private GeierReachBandit(final GeierReachBandit card) {
        super(card);
    }

    @Override
    public GeierReachBandit copy() {
        return new GeierReachBandit(this);
    }
}