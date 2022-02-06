
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class TerritorialHammerskull extends CardImpl {

    public TerritorialHammerskull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Territorial Hammerskull attacks, tap target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private TerritorialHammerskull(final TerritorialHammerskull card) {
        super(card);
    }

    @Override
    public TerritorialHammerskull copy() {
        return new TerritorialHammerskull(this);
    }
}
