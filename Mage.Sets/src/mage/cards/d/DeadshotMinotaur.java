
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class DeadshotMinotaur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DeadshotMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.MINOTAUR);



        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Deadshot Minotaur enters the battlefield, it deals 3 damage to target creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // Cycling {RG}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R/G}")));
    }

    private DeadshotMinotaur(final DeadshotMinotaur card) {
        super(card);
    }

    @Override
    public DeadshotMinotaur copy() {
        return new DeadshotMinotaur(this);
    }
}
