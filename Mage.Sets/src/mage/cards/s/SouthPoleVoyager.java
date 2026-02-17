package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SouthPoleVoyager extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ALLY, "Ally");

    public SouthPoleVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature or another Ally you control enters, you gain 1 life. If this is the second time this ability has resolved this turn, draw a card.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(new GainLifeEffect(1), filter, false, true);
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(2, new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private SouthPoleVoyager(final SouthPoleVoyager card) {
        super(card);
    }

    @Override
    public SouthPoleVoyager copy() {
        return new SouthPoleVoyager(this);
    }
}
