package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcebergTitan extends CardImpl {

    public IcebergTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.nightCard = true;
        this.color.setBlue(true);

        // Whenever Iceberg Titan attacks, you may tap or untap target artifact or creature.
        Ability ability = new AttacksTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private IcebergTitan(final IcebergTitan card) {
        super(card);
    }

    @Override
    public IcebergTitan copy() {
        return new IcebergTitan(this);
    }
}
