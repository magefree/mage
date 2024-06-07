package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KozilekTheBrokenReality extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("colorless creatures");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public KozilekTheBrokenReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When you cast this spell, up to two target players each manifest two cards from their hands. For each card manifested this way, you draw a card.
        Ability ability = new CastSourceTriggeredAbility(new KozilekTheBrokenRealityEffect());
        ability.addTarget(new TargetPlayer(0, 2, false));
        this.addAbility(ability);

        // Other colorless creatures you control get +3/+2.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(3, 2, Duration.WhileOnBattlefield, filter, true)
        ));
    }

    private KozilekTheBrokenReality(final KozilekTheBrokenReality card) {
        super(card);
    }

    @Override
    public KozilekTheBrokenReality copy() {
        return new KozilekTheBrokenReality(this);
    }
}

class KozilekTheBrokenRealityEffect extends OneShotEffect {

    KozilekTheBrokenRealityEffect() {
        super(Outcome.Neutral);
        staticText = "up to two target players each manifest two cards from their hands. For each card manifested this way, you draw a card";
    }

    private KozilekTheBrokenRealityEffect(final KozilekTheBrokenRealityEffect effect) {
        super(effect);
    }

    @Override
    public KozilekTheBrokenRealityEffect copy() {
        return new KozilekTheBrokenRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        boolean result = false;
        int toDraw = 0;
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amount = Math.min(2, player.getHand().size());
            if (amount <= 0) {
                continue;
            }
            TargetCardInHand target = new TargetCardInHand(amount, StaticFilters.FILTER_CARD).withChooseHint("to manifest");
            target.choose(Outcome.Discard, playerId, source.getId(), source, game);
            Cards toManifest = new CardsImpl(target.getTargets());
            toDraw += ManifestEffect.doManifestCards(game, source, player, toManifest.getCards(game)).size();
            result = true;
        }
        if (toDraw > 0) {
            controller.drawCards(toDraw, source, game);
        }
        return result;
    }

}