package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsparingBoltcaster extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public UnsparingBoltcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, it deals 5 damage to target creature an opponent controls that was dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private UnsparingBoltcaster(final UnsparingBoltcaster card) {
        super(card);
    }

    @Override
    public UnsparingBoltcaster copy() {
        return new UnsparingBoltcaster(this);
    }
}
