package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimbleBrigand extends CardImpl {

    public NimbleBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Nimble Brigand can't be blocked if you've committed a crime this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), CommittedCrimeCondition.instance,
                "{this} can't be blocked if you've committed a crime this turn"
        )).addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());

        // Whenever Nimble Brigand deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private NimbleBrigand(final NimbleBrigand card) {
        super(card);
    }

    @Override
    public NimbleBrigand copy() {
        return new NimbleBrigand(this);
    }
}
