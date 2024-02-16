package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FolkHero extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that shares a creature type with this creature");

    static {
        filter.add(FolkHeroPredicate.instance);
    }

    public FolkHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever you cast a spell that shares a creature type with this creature, draw a card. This ability triggers only once each turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new SpellCastControllerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), filter, false
                ).setTriggersOnceEachTurn(true), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private FolkHero(final FolkHero card) {
        super(card);
    }

    @Override
    public FolkHero copy() {
        return new FolkHero(this);
    }
}

enum FolkHeroPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
                .map(permanent -> input.getObject().shareCreatureTypes(game, permanent))
                .orElse(false);
    }
}
