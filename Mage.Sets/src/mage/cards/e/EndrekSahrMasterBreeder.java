
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.ThrullToken;
import mage.game.stack.Spell;

/**
 * @author LevelX2
 */
public final class EndrekSahrMasterBreeder extends CardImpl {

    public EndrekSahrMasterBreeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, create X 1/1 black Thrull creature tokens, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new EndrekSahrMasterBreederEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false, true));
        // When you control seven or more Thrulls, sacrifice Endrek Sahr, Master Breeder.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterCreaturePermanent(SubType.THRULL, "seven or more Thrulls"), ComparisonType.MORE_THAN, 6,
                new SacrificeSourceEffect()));
    }

    private EndrekSahrMasterBreeder(final EndrekSahrMasterBreeder card) {
        super(card);
    }

    @Override
    public EndrekSahrMasterBreeder copy() {
        return new EndrekSahrMasterBreeder(this);
    }
}

class EndrekSahrMasterBreederEffect extends OneShotEffect {

    public EndrekSahrMasterBreederEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 1/1 black Thrull creature tokens, where X is that spell's mana value";
    }

    public EndrekSahrMasterBreederEffect(final EndrekSahrMasterBreederEffect effect) {
        super(effect);
    }

    @Override
    public EndrekSahrMasterBreederEffect copy() {
        return new EndrekSahrMasterBreederEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getManaValue();
            if (cmc > 0) {
                return new CreateTokenEffect(new ThrullToken(), cmc).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
