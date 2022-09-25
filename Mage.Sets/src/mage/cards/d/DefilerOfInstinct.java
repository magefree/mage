package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.MayPay2LifeForColorAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefilerOfInstinct extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red permanent spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(PermanentPredicate.instance);
    }

    public DefilerOfInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As an additional cost to cast red permanent spells, you may pay 2 life. Those spells cost {R} less to cast if you paid life this way. This effect reduces only the amount of red mana you pay.
        this.addAbility(new MayPay2LifeForColorAbility(ObjectColor.RED));

        // Whenever you cast a red permanent spell, Defiler of Instinct deals 1 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(1), filter, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DefilerOfInstinct(final DefilerOfInstinct card) {
        super(card);
    }

    @Override
    public DefilerOfInstinct copy() {
        return new DefilerOfInstinct(this);
    }
}
