package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class TakenosCavalry extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature("attacking or blocking Spirit");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public TakenosCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BushidoAbility(1));
        // {tap}: Takeno's Cavalry deals 1 damage to target attacking or blocking Spirit.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TakenosCavalry(final TakenosCavalry card) {
        super(card);
    }

    @Override
    public TakenosCavalry copy() {
        return new TakenosCavalry(this);
    }
}
