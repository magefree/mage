package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuickbeamUpstartEnt extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.TREEFOLK, "Treefolk");

    public QuickbeamUpstartEnt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Whenever Quickbeam, Upstart Ent or another Treefolk enters the battlefield under your control, up to two target creatures each get +2/+2 and gain trample until the end of turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("up to two target creatures each get +2/+2"),
                filter, false, true
        );
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gain trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private QuickbeamUpstartEnt(final QuickbeamUpstartEnt card) {
        super(card);
    }

    @Override
    public QuickbeamUpstartEnt copy() {
        return new QuickbeamUpstartEnt(this);
    }
}
