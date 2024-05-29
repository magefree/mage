package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author grimreap124
 */
public final class MonstrousVortex extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a creature spell with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public MonstrousVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{3}{G}");

        // Whenever you cast a creature spell with power 5 or greater, discover X, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MonstrousVortexEffect(), filter, false,
                SetTargetPointer.SPELL));
    }

    private MonstrousVortex(final MonstrousVortex card) {
        super(card);
    }

    @Override
    public MonstrousVortex copy() {
        return new MonstrousVortex(this);
    }
}

class MonstrousVortexEffect extends OneShotEffect {

    MonstrousVortexEffect() {
        super(Outcome.Benefit);
        this.staticText = "discover X, where X is that spell's mana value";
    }

    private MonstrousVortexEffect(final MonstrousVortexEffect effect) {
        super(effect);
    }

    @Override
    public MonstrousVortexEffect copy() {
        return new MonstrousVortexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            new DiscoverEffect(spell.getManaValue()).apply(game, source);
        }
        return true;
    }
}