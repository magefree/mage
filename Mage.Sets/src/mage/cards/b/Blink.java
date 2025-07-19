package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AlienAngelToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blink extends CardImpl {

    public Blink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, III -- Choose target creature. Its owner shuffles it into their library, then investigates.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new BlinkEffect(), new TargetCreaturePermanent()
        );
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new BlinkEffect(), new TargetCreaturePermanent()
        );

        // II, IV -- Create a 2/2 black Alien Angel artifact creature token with first strike, vigilance, and "Whenever an opponent casts a creature spell, this permanent isn't a creature until end of turn."
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new AlienAngelToken())
        );
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new CreateTokenEffect(new AlienAngelToken())
        );
        this.addAbility(sagaAbility); //TODO: These should be a single AddChapterEffect, but currently XMage does not support noncontiguous Saga chapters
    }

    private Blink(final Blink card) {
        super(card);
    }

    @Override
    public Blink copy() {
        return new Blink(this);
    }
}

class BlinkEffect extends OneShotEffect {

    BlinkEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature. Its owner shuffles it into their library, then investigates";
    }

    private BlinkEffect(final BlinkEffect effect) {
        super(effect);
    }

    @Override
    public BlinkEffect copy() {
        return new BlinkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        if (player == null) {
            return false;
        }
        player.shuffleCardsToLibrary(permanent, game, source);
        InvestigateEffect.doInvestigate(player.getId(), 1, game, source);
        return true;
    }
}
