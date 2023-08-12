package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class OrcSureshot extends CardImpl {

    public OrcSureshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new BoostTargetEffect(-1,-1, Duration.EndOfTurn),
                StaticFilters.FILTER_ANOTHER_CREATURE);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private OrcSureshot(final OrcSureshot card) {
        super(card);
    }

    @Override
    public OrcSureshot copy() {
        return new OrcSureshot(this);
    }
}
