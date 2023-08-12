
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TiborAndLumia extends CardImpl {

    private static final FilterSpell filterBlue = new FilterSpell("a blue spell");
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    private static final FilterSpell filterRed = new FilterSpell("a red spell");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    public TiborAndLumia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a blue spell, target creature gains flying until end of turn.
        Ability firstAbility = new SpellCastControllerTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), filterBlue, false);
        firstAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(firstAbility);
        // Whenever you cast a red spell, Tibor and Lumia deals 1 damage to each creature without flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DamageAllEffect(1, filter), filterRed, false));

    }

    private TiborAndLumia(final TiborAndLumia card) {
        super(card);
    }

    @Override
    public TiborAndLumia copy() {
        return new TiborAndLumia(this);
    }
}
