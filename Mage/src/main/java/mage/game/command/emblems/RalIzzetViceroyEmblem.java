package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class RalIzzetViceroyEmblem extends Emblem {

    // You get an emblem with "Whenever you cast an instant or sorcery spell, this emblem deals 4 damage to any target and you draw two cards."
    public RalIzzetViceroyEmblem() {
        super("Emblem Ral");
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.COMMAND, new DamageTargetEffect(4, "this emblem"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, false
        );
        ability.addEffect(
                new DrawCardSourceControllerEffect(2)
                        .setText("and you draw two cards")
        );
        ability.addTarget(new TargetAnyTarget());
        getAbilities().add(ability);
    }

    private RalIzzetViceroyEmblem(final RalIzzetViceroyEmblem card) {
        super(card);
    }

    @Override
    public RalIzzetViceroyEmblem copy() {
        return new RalIzzetViceroyEmblem(this);
    }
}
