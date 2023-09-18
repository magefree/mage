package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearfulVillager extends CardImpl {

    public FearfulVillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.f.FearsomeWerewolf.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private FearfulVillager(final FearfulVillager card) {
        super(card);
    }

    @Override
    public FearfulVillager copy() {
        return new FearfulVillager(this);
    }
}
