package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Ludwig
 */

public final class TellerOfTales extends CardImpl {

    // Outcome.Benefit, "tap or untap target creature"

    public TellerOfTales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may tap or untap target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new MayTapOrUntapTargetEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TellerOfTales(final TellerOfTales card) {
        super(card);
    }

    @Override
    public TellerOfTales copy() {
        return new TellerOfTales(this);
    }

}
