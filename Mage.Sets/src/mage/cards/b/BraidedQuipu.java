package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BraidedQuipu extends CardImpl {

    public BraidedQuipu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;
        this.color.setBlue(true);

        // {3}{U}, {T}: Draw a card for each artifact you control, then put Braided Quipu into its owner's library third from the top.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(ArtifactYouControlCount.instance), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new BraidedQuipuEffect());
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));
    }

    private BraidedQuipu(final BraidedQuipu card) {
        super(card);
    }

    @Override
    public BraidedQuipu copy() {
        return new BraidedQuipu(this);
    }
}

class BraidedQuipuEffect extends OneShotEffect {

    BraidedQuipuEffect() {
        super(Outcome.Benefit);
        staticText = ", then put {this} into its owner's library third from the top";
    }

    private BraidedQuipuEffect(final BraidedQuipuEffect effect) {
        super(effect);
    }

    @Override
    public BraidedQuipuEffect copy() {
        return new BraidedQuipuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return player != null
                && permanent != null
                && player.putCardOnTopXOfLibrary(permanent, game, source, 3, true);
    }
}
