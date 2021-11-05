package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.DragonIllusionToken;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 *
 * @author weirddan455
 */
public final class ManaformHellkite extends CardImpl {

    public ManaformHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, create an X/X red Dragon Illusion creature token with flying and haste, where X is the amount of mana spent to cast that spell.
        // Exile that token at the beginning of the next end step.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ManaformHellkitEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, true));
    }

    private ManaformHellkite(final ManaformHellkite card) {
        super(card);
    }

    @Override
    public ManaformHellkite copy() {
        return new ManaformHellkite(this);
    }
}

class ManaformHellkitEffect extends OneShotEffect {

    public ManaformHellkitEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X red Dragon Illusion creature token with flying and haste, where X is the amount of mana spent to cast that spell. "
                + "Exile that token at the beginning of the next end step";
    }

    private ManaformHellkitEffect(final ManaformHellkitEffect effect) {
        super(effect);
    }

    @Override
    public ManaformHellkitEffect copy() {
        return new ManaformHellkitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new DragonIllusionToken(ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game)));
            if (effect.apply(game, source)) {
                effect.exileTokensCreatedAtNextEndStep(game, source);
                return true;
            }
        }
        return false;
    }
}
