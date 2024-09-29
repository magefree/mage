package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurtrFieryJotun extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();

    public SurtrFieryJotun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a historic spell, Surtr, Fiery Jotun deals 3 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(3), filter, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SurtrFieryJotun(final SurtrFieryJotun card) {
        super(card);
    }

    @Override
    public SurtrFieryJotun copy() {
        return new SurtrFieryJotun(this);
    }
}
