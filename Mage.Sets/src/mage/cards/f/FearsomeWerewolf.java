package mage.cards.f;

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
public final class FearsomeWerewolf extends CardImpl {

    public FearsomeWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private FearsomeWerewolf(final FearsomeWerewolf card) {
        super(card);
    }

    @Override
    public FearsomeWerewolf copy() {
        return new FearsomeWerewolf(this);
    }
}
