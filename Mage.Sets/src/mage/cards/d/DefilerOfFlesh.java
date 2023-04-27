package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.MayPay2LifeForColorAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefilerOfFlesh extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a black permanent spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(PermanentPredicate.instance);
    }

    public DefilerOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // As an additional cost to cast black permanent spells, you may pay 2 life. Those spells cost {B} less to cast if you paid life this way. This effect reduces only the amount of black mana you pay.
        this.addAbility(new MayPay2LifeForColorAbility(ObjectColor.BLACK));

        // Whenever you cast a black permanent spell, target creature you control gets +1/+1 and gains menace until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostTargetEffect(1, 1)
                        .setText("target creature you control gets +1/+1"),
                filter, false
        );
        ability.addEffect(new GainAbilityTargetEffect(new MenaceAbility(false))
                .setText("and gains menace until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DefilerOfFlesh(final DefilerOfFlesh card) {
        super(card);
    }

    @Override
    public DefilerOfFlesh copy() {
        return new DefilerOfFlesh(this);
    }
}
