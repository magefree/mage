package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BothersomeQuasit extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("goaded creatures your opponents control");

    static {
        filter.add(GoadedPredicate.instance);
    }

    public BothersomeQuasit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Goaded creatures your opponents control can't block.
        this.addAbility(new SimpleStaticAbility(
                new CantBlockAllEffect(filter, Duration.WhileOnBattlefield)
        ));

        // Whenever you cast a noncreature spell, goad target creature an opponent controls.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new GoadTargetEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BothersomeQuasit(final BothersomeQuasit card) {
        super(card);
    }

    @Override
    public BothersomeQuasit copy() {
        return new BothersomeQuasit(this);
    }
}
