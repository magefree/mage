package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author muz
 */
public final class GreatHallOfTheBiblioplex extends CardImpl {

    public GreatHallOfTheBiblioplex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay 1 life: Add one mana of any color. Spend this mana only to cast an instant or sorcery spell.
        Ability ability = new ConditionalAnyColorManaAbility(1, new InstantOrSorcerySpellManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {5}: If this land isn't a creature, it becomes a 2/4 Wizard creature with "Whenever you cast an instant or sorcery spell, this creature gets +1/+0 until end of turn." It's still a land.
        ability = new SimpleActivatedAbility(new GreatHallOfTheBiblioplexEffect(), new GenericManaCost(5));
        this.addAbility(ability);
    }

    private GreatHallOfTheBiblioplex(final GreatHallOfTheBiblioplex card) {
        super(card);
    }

    @Override
    public GreatHallOfTheBiblioplex copy() {
        return new GreatHallOfTheBiblioplex(this);
    }
}

class GreatHallOfTheBiblioplexEffect extends OneShotEffect {

    GreatHallOfTheBiblioplexEffect() {
        super(Outcome.Benefit);
        staticText = "If this land isn't a creature, it becomes a 2/4 Wizard creature with " +
                "\"Whenever you cast an instant or sorcery spell, this creature gets +1/+0 until end of turn.\" It's still a land.";
    }

    private GreatHallOfTheBiblioplexEffect(final GreatHallOfTheBiblioplexEffect effect) {
        super(effect);
    }

    @Override
    public GreatHallOfTheBiblioplexEffect copy() {
        return new GreatHallOfTheBiblioplexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        if (permanent.isCreature(game)) {
            return true;
        }
        game.addEffect(new BecomesCreatureSourceEffect(
            new CreatureToken(2, 4, "2/4 Wizard creature with \"Whenever you cast an instant or sorcery spell, this creature gets +1/+0 until end of turn.\"", SubType.WIZARD)
                .withAbility(new SpellCastControllerTriggeredAbility(
                    new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                    StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                    false
                )),
            CardType.LAND,
            Duration.Custom
        ), source);
        return true;
    }
}
