package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.WardAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author rullinoiz
 */
public final class SarumanTheWhiteHand extends CardImpl {

    private static final FilterPermanent orcAndGoblinFilter = new FilterPermanent("Goblins and Orcs");

    static {
        orcAndGoblinFilter.add(Predicates.or(
                SubType.ORC.getPredicate(),
                SubType.GOBLIN.getPredicate()
        ));
    }

    public SarumanTheWhiteHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever you cast a noncreature spell, amass Orcs X, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SarumanTheWhiteHandEffect(),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false
        ));

        // Goblins and Orcs you control have ward {2}.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                    new WardAbility(new GenericManaCost(2), false),
                    Duration.WhileOnBattlefield,
                    orcAndGoblinFilter
                )
        ));
    }

    private SarumanTheWhiteHand(final SarumanTheWhiteHand card) {
        super(card);
    }

    @Override
    public SarumanTheWhiteHand copy() {
        return new SarumanTheWhiteHand(this);
    }
}

class SarumanTheWhiteHandEffect extends OneShotEffect {

    public SarumanTheWhiteHandEffect() {
        super(Outcome.Benefit);
        staticText = "amass Orcs X, where X is that spell's mana value";
    }

    private SarumanTheWhiteHandEffect(final SarumanTheWhiteHandEffect effect) { super(effect); }

    @Override
    public SarumanTheWhiteHandEffect copy() { return new SarumanTheWhiteHandEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && AmassEffect.doAmass(spell.getManaValue(), SubType.ORC, game, source) != null;
    }
}