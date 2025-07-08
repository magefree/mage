package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noahg
 */
public final class MuseVessel extends CardImpl {

    public MuseVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        // {3}, {tap}: Target player exiles a card from their hand. Activate this ability only any time you could cast a sorcery.
        ActivateAsSorceryActivatedAbility tapAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new MuseVesselExileEffect(), new TapSourceCost());
        tapAbility.addCost(new ManaCostsImpl<>("{3}"));
        tapAbility.addTarget(new TargetPlayer());
        this.addAbility(tapAbility);
        // {1}: Choose a card exiled with Muse Vessel. You may play that card this turn.
        SimpleActivatedAbility playAbility = new SimpleActivatedAbility(new OneShotNonTargetEffect(
                new AddContinuousEffectToGame(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn))
                        .setText("Choose a card exiled with {this}. You may play that card this turn."),
                new TargetCardInExile(StaticFilters.FILTER_CARD), MuseVesselAdjuster.instance
        ), new ManaCostsImpl<>("{1}"));
        this.addAbility(playAbility);
    }

    private MuseVessel(final MuseVessel card) {
        super(card);
    }

    @Override
    public MuseVessel copy() {
        return new MuseVessel(this);
    }
}

class MuseVesselExileEffect extends OneShotEffect {

    MuseVesselExileEffect() {
        super(Outcome.Exile);
        staticText = "target player exiles a card from their hand";
    }

    private MuseVesselExileEffect(final MuseVesselExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)
                && target.choose(Outcome.Exile, player.getId(), source, game)) {
            UUID exileId = CardUtil.getExileZoneId(game, source);
            return player.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, true, exileId, sourceObject.getIdName());
        }
        return false;
    }

    @Override
    public MuseVesselExileEffect copy() {
        return new MuseVesselExileEffect(this);
    }

}

enum MuseVesselAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(
                new TargetCardInExile(StaticFilters.FILTER_CARD, CardUtil.getCardExileZoneId(game, ability.getSourceId()))
                        .withNotTarget(true));
    }
}
