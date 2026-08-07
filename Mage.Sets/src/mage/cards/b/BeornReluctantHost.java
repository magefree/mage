package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class BeornReluctantHost extends AdventureCard {

    public BeornReluctantHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{G}", "Till and Tend", "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Till and Tend
        // You may play an additional land this turn.
        this.getSpellCard().getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        this.finalizeAdventure();
    }

    private BeornReluctantHost(final BeornReluctantHost card) {
        super(card);
    }

    @Override
    public BeornReluctantHost copy() {
        return new BeornReluctantHost(this);
    }
}
