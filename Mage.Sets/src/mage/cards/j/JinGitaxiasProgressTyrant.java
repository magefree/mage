package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JinGitaxiasProgressTyrant extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact, instant, or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public JinGitaxiasProgressTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you cast an artifact, instant, or sorcery spell, copy that spell. You may choose new targets for the copy. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopySourceSpellEffect().setText("copy that spell. You may choose new targets for the copy"),
                filter, false, true
        ).setTriggersOnceEachTurn(true));

        // Whenever an opponent casts an artifact, instant, or sorcery spell, counter that spell. This ability triggers only once each turn.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new JinGitaxiasProgressTyrantEffect(), filter, false
        ).setTriggersOnceEachTurn(true));
    }

    private JinGitaxiasProgressTyrant(final JinGitaxiasProgressTyrant card) {
        super(card);
    }

    @Override
    public JinGitaxiasProgressTyrant copy() {
        return new JinGitaxiasProgressTyrant(this);
    }
}

class JinGitaxiasProgressTyrantEffect extends OneShotEffect {

    JinGitaxiasProgressTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "counter that spell";
    }

    private JinGitaxiasProgressTyrantEffect(final JinGitaxiasProgressTyrantEffect effect) {
        super(effect);
    }

    @Override
    public JinGitaxiasProgressTyrantEffect copy() {
        return new JinGitaxiasProgressTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            game.getStack().counter(spell.getId(), source, game);;
        }
        return true;
    }
}
