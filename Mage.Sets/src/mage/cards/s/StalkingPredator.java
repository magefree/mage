package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalkingPredator extends CardImpl {

    public StalkingPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private StalkingPredator(final StalkingPredator card) {
        super(card);
    }

    @Override
    public StalkingPredator copy() {
        return new StalkingPredator(this);
    }
}
