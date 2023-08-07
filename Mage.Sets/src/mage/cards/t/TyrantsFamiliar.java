
package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

/**
 * @author emerald000
 */
public final class TyrantsFamiliar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature defending player controls");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public TyrantsFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Lieutenant - As long as you control your commander, Tyrant's Familiar gets +2/+2 and has "Whenever Tyrant's Familiar attacks, it deals 7 damage to target creature defending player controls."
        Ability gainedAbility = new AttacksTriggeredAbility(new DamageTargetEffect(7, "it"), false);
        gainedAbility.addTarget(new TargetPermanent(filter));
        ContinuousEffect effect = new GainAbilitySourceEffect(gainedAbility);
        effect.setText("and has \"Whenever {this} attacks, it deals 7 damage to target creature defending player controls.\"");
        this.addAbility(new LieutenantAbility(effect));
    }

    private TyrantsFamiliar(final TyrantsFamiliar card) {
        super(card);
    }

    @Override
    public TyrantsFamiliar copy() {
        return new TyrantsFamiliar(this);
    }
}
