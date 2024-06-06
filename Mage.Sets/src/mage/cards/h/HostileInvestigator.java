package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DiscardCardPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HostileInvestigator extends CardImpl {

    public HostileInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Hostile Investigator enters the battlefield, target opponent discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever one or more players discard one or more cards, investigate. This ability triggers only once each turn.
        this.addAbility(new DiscardCardPlayerTriggeredAbility(new InvestigateEffect(), false)
                .setTriggerPhrase("Whenever one or more players discard one or more cards, ")
                .setTriggersLimitEachTurn(1));
    }

    private HostileInvestigator(final HostileInvestigator card) {
        super(card);
    }

    @Override
    public HostileInvestigator copy() {
        return new HostileInvestigator(this);
    }
}
