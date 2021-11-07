package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BallistaWatcher extends CardImpl {

    public BallistaWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BallistaWielder.class;

        // {2}{R}, {T}: Ballista Watcher deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private BallistaWatcher(final BallistaWatcher card) {
        super(card);
    }

    @Override
    public BallistaWatcher copy() {
        return new BallistaWatcher(this);
    }
}
