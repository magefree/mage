package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class NumotTheDevastator extends CardImpl {

    public NumotTheDevastator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Numot, the Devastator deals combat damage to a player, you may pay {2}{R}. If you do, destroy up to two target lands.
        OneShotEffect effect = new DestroyTargetEffect();
        effect.setText("destroy up to two target lands");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(effect, new ManaCostsImpl<>("{2}{R}")), false);
        ability.addTarget(new TargetLandPermanent(0, 2));
        this.addAbility(ability);
    }

    private NumotTheDevastator(final NumotTheDevastator card) {
        super(card);
    }

    @Override
    public NumotTheDevastator copy() {
        return new NumotTheDevastator(this);
    }
}
