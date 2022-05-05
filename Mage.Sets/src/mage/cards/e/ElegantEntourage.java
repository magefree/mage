package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElegantEntourage extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ElegantEntourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Alliance — Whenever another creature enters the battlefield under your control, target creature other than Elegant Entourage gets +1/+1 and gains trample until end of turn.
        Ability ability = new AllianceAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("target creature other than {this} gets +1/+1"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElegantEntourage(final ElegantEntourage card) {
        super(card);
    }

    @Override
    public ElegantEntourage copy() {
        return new ElegantEntourage(this);
    }
}
