package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Cguy7777
 */
public final class AggressiveBiomancy extends CardImpl {

    public AggressiveBiomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}{U}");

        // Create X tokens that are copies of target creature you control, except they have
        // "When this creature enters the battlefield, it fights up to one target creature you don't control."
        this.getSpellAbility().addEffect(new AggressiveBiomancyEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private AggressiveBiomancy(final AggressiveBiomancy card) {
        super(card);
    }

    @Override
    public AggressiveBiomancy copy() {
        return new AggressiveBiomancy(this);
    }
}

class AggressiveBiomancyEffect extends OneShotEffect {

    AggressiveBiomancyEffect() {
        super(Outcome.Copy);
        this.staticText = "Create X tokens that are copies of target creature you control, " +
                "except they have \"When this creature enters the battlefield, " +
                "it fights up to one target creature you don't control.\"";
    }

    private AggressiveBiomancyEffect(final AggressiveBiomancyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creatureToCopy = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller == null || creatureToCopy == null) {
            return false;
        }

        Ability fightAbility = new EntersBattlefieldTriggeredAbility(
                new FightTargetSourceEffect().setText("it fights up to one target creature you don't control"));
        fightAbility.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(),
                null,
                false,
                source.getManaCostsToPay().getX());
        effect.addAdditionalAbilities(fightAbility);
        effect.setTargetPointer(new FixedTarget(creatureToCopy, game));
        return effect.apply(game, source);
    }

    @Override
    public AggressiveBiomancyEffect copy() {
        return new AggressiveBiomancyEffect(this);
    }
}
