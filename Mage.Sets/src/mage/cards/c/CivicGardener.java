package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class CivicGardener extends CardImpl {

    public CivicGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Civic Gardener attacks, untap target creature or land.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
        this.addAbility(ability);
    }

    private CivicGardener(final CivicGardener card) {
        super(card);
    }

    @Override
    public CivicGardener copy() {
        return new CivicGardener(this);
    }
}
