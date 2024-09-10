package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Stormsplitter extends CardImpl {

    public Stormsplitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, create a token that's a copy of Stormsplitter. Exile that token at the beginning of the next end step.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new StormsplitterEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private Stormsplitter(final Stormsplitter card) {
        super(card);
    }

    @Override
    public Stormsplitter copy() {
        return new Stormsplitter(this);
    }
}

class StormsplitterEffect extends OneShotEffect {

    StormsplitterEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of {this}. " +
                "Exile that token at the beginning of the next end step";
    }

    private StormsplitterEffect(final StormsplitterEffect effect) {
        super(effect);
    }

    @Override
    public StormsplitterEffect copy() {
        return new StormsplitterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
