package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElrondLordOfRivendell extends CardImpl {

    public ElrondLordOfRivendell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Elrond, Lord of Rivendell or another creature enters the battlefield under your control, scry 1. If this is the second time this ability has resolved this turn, the Ring tempts you.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ScryEffect(1, false),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(2, new TheRingTemptsYouEffect()));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private ElrondLordOfRivendell(final ElrondLordOfRivendell card) {
        super(card);
    }

    @Override
    public ElrondLordOfRivendell copy() {
        return new ElrondLordOfRivendell(this);
    }
}
