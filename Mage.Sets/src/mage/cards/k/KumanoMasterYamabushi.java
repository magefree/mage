package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class KumanoMasterYamabushi extends CardImpl {

    public KumanoMasterYamabushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{R}: Kumano, Master Yamabushi deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // If a creature dealt damage by Kumano this turn would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DealtDamageToCreatureBySourceDies(this, Duration.WhileOnBattlefield)), new DamagedByWatcher(false));

    }

    private KumanoMasterYamabushi(final KumanoMasterYamabushi card) {
        super(card);
    }

    @Override
    public KumanoMasterYamabushi copy() {
        return new KumanoMasterYamabushi(this);
    }

}
