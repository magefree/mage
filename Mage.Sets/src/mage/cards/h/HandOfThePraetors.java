package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author Viserion, North
 */
public final class HandOfThePraetors extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with infect");
    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with infect");

    static {
        filter.add(new AbilityPredicate(InfectAbility.class));
        filterSpell.add(new AbilityPredicate(InfectAbility.class));
        filterSpell.add(CardType.CREATURE.getPredicate());
    }

    public HandOfThePraetors (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new AddPoisonCounterTargetEffect(1), filterSpell, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private HandOfThePraetors(final HandOfThePraetors card) {
        super(card);
    }

    @Override
    public HandOfThePraetors copy() {
        return new HandOfThePraetors(this);
    }

}
