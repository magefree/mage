
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class TattermungeWitch extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("Each blocked creature");

    static {
        filter.add(BlockedPredicate.instance);
    }

    public TattermungeWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}{G}: Each blocked creature gets +1/+0 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false).setText("each blocked creature gets +1/+0"), new ManaCostsImpl<>("{R}{G}"));
        ability.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter, "and gains trample until end of turn"));
        this.addAbility(ability);
        
    }

    private TattermungeWitch(final TattermungeWitch card) {
        super(card);
    }

    @Override
    public TattermungeWitch copy() {
        return new TattermungeWitch(this);
    }
}
