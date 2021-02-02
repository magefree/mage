
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author LevelX2
 */
public final class CavalryPegasus extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("each attacking Human");
    static {
        filter.add(SubType.HUMAN.getPredicate());
        filter.add(AttackingPredicate.instance);
    }

    public CavalryPegasus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.PEGASUS);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Cavalry Pegasus attacks, each attacking Human gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter), false));


    }

    private CavalryPegasus(final CavalryPegasus card) {
        super(card);
    }

    @Override
    public CavalryPegasus copy() {
        return new CavalryPegasus(this);
    }
}
