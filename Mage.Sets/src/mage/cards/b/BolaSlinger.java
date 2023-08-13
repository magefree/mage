package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.BackupAbility;
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
public final class BolaSlinger extends CardImpl {

    public BolaSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Whenever this creature attacks, tap target artifact or creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect()).setTriggerPhrase("Whenever this creature attacks, ");
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        backupAbility.addAbility(ability);
    }

    private BolaSlinger(final BolaSlinger card) {
        super(card);
    }

    @Override
    public BolaSlinger copy() {
        return new BolaSlinger(this);
    }
}
