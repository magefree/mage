package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class GrakkThePacifist extends CardImpl {

    private static final FilterSpell filterAura = new FilterSpell("an Aura spell that targets a creature an opponent controls");

    static {
        filterAura.add(SubType.AURA.getPredicate());
        filterAura.add(new TargetsPermanentPredicate(StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
    }

    public GrakkThePacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Grakk can't attack or block.
        SimpleStaticAbility ability = new SimpleStaticAbility(
            new CantAttackSourceEffect(Duration.WhileOnBattlefield)
        );
        ability.addEffect(new CantBlockSourceEffect(Duration.WhileOnBattlefield).setText("or block"));
        this.addAbility(ability);

        // Whenever you cast an Aura spell that targets a creature an opponent controls, draw a card. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filterAura, false
        ).setTriggersLimitEachTurn(1));
    }

    private GrakkThePacifist(final GrakkThePacifist card) {
        super(card);
    }

    @Override
    public GrakkThePacifist copy() {
        return new GrakkThePacifist(this);
    }
}
