package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ServantOfTheStinger extends CardImpl {

    public ServantOfTheStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Servant of the Stinger deals combat damage to a player, if you've committed a crime this turn, you may sacrifice Servant of the Stinger. If you do, search your library for a card, put it into your hand, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DoIfCostPaid(
                                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false),
                                new SacrificeSourceCost()
                        ), false
                ), CommittedCrimeCondition.instance, "Whenever {this} deals combat damage to a player, " +
                "if you've committed a crime this turn, you may sacrifice {this}. If you do, " +
                "search your library for a card, put it into your hand, then shuffle."
        ).addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());
    }

    private ServantOfTheStinger(final ServantOfTheStinger card) {
        super(card);
    }

    @Override
    public ServantOfTheStinger copy() {
        return new ServantOfTheStinger(this);
    }
}
