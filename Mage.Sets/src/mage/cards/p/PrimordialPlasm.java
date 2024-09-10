package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimordialPlasm extends CardImpl {

    public PrimordialPlasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);
        this.nightCard = true;

        // At the beginning of combat on your turn, another target creature gets +2/+2 and loses all abilities until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("another target creature gets +2/+2"),
                TargetController.YOU, false
        );
        ability.addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("and loses all abilities until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private PrimordialPlasm(final PrimordialPlasm card) {
        super(card);
    }

    @Override
    public PrimordialPlasm copy() {
        return new PrimordialPlasm(this);
    }
}
