package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfUlgrotha extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public InvasionOfUlgrotha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{4}{B}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.g.GrandmotherRaviSengir.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Ulgrotha enters the battlefield, it deals 3 damage to any other target and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);
    }

    private InvasionOfUlgrotha(final InvasionOfUlgrotha card) {
        super(card);
    }

    @Override
    public InvasionOfUlgrotha copy() {
        return new InvasionOfUlgrotha(this);
    }
}
