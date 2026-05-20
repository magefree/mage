package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeechCollector extends PrepareCard {

    public LeechCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}", "Bloodletting", CardType.SORCERY, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life for the first time each turn, this creature becomes prepared.
        this.addAbility(new GainLifeFirstTimeTriggeredAbility(new BecomePreparedSourceEffect()));

        // Bloodletting
        // Sorcery {B}
        // Each opponent loses 2 life.
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeOpponentsEffect(2));
    }

    private LeechCollector(final LeechCollector card) {
        super(card);
    }

    @Override
    public LeechCollector copy() {
        return new LeechCollector(this);
    }
}
