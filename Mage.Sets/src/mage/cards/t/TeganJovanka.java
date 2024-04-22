package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TeganJovanka extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking historic creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(HistoricPredicate.instance);
    }

    public TeganJovanka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Brave Heart -- Whenever you attack, target attacking historic creature gets +1/+1 and gains indestructible until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new BoostTargetEffect(1, 1)
                        .setText("target attacking historic creature gets +1/+1"),
                1
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and gains indestructible until end of turn"));
        this.addAbility(ability.withFlavorWord("Brave Heart"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private TeganJovanka(final TeganJovanka card) {
        super(card);
    }

    @Override
    public TeganJovanka copy() {
        return new TeganJovanka(this);
    }
}
