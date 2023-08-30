package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class InitiatesCompanion extends CardImpl {

    public InitiatesCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Initiate's Companion deals combat damage to a player, untap target creature or land.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
        this.addAbility(ability);
    }

    private InitiatesCompanion(final InitiatesCompanion card) {
        super(card);
    }

    @Override
    public InitiatesCompanion copy() {
        return new InitiatesCompanion(this);
    }
}
