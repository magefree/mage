package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.common.FilterBattlePermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfRegatha extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer(
            "another target permanent or player",
            new FilterBattlePermanent(), new FilterOpponent()
    );

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public InvasionOfRegatha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.d.DisciplesOfTheInferno.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Regatha enters the battlefield, it deals 4 damage to another target battle or opponent and 1 damage to up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(
                        4, true,
                        "another target battle or opponent", "it"

                ).setUseOnlyTargetPointer(true)
        );
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        ability.addEffect(new DamageTargetEffect(1)
                .setUseOnlyTargetPointer(true)
                .setTargetPointer(new SecondTargetPointer())
                .setText("and 1 damage to up to one target creature"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private InvasionOfRegatha(final InvasionOfRegatha card) {
        super(card);
    }

    @Override
    public InvasionOfRegatha copy() {
        return new InvasionOfRegatha(this);
    }
}
