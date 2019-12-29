package mage.cards.u;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.KarnConstructToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaLordHighArtificer extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("untapped artifact you control");

    static {
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    public UrzaLordHighArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Urza, Lord High Artificer enters the battlefield, create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KarnConstructToken())));

        // Tap an untapped artifact you control: Add {U}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.BlueMana(1), new TapTargetCost(new TargetControlledPermanent(filter))
        ));

        // {5}: Shuffle your library, then exile the top card. Until end of turn, you may play that card without paying its mana cost.
        this.addAbility(new SimpleActivatedAbility(new UrzaLordHighArtificerEffect(), new GenericManaCost(5)));
    }

    private UrzaLordHighArtificer(final UrzaLordHighArtificer card) {
        super(card);
    }

    @Override
    public UrzaLordHighArtificer copy() {
        return new UrzaLordHighArtificer(this);
    }
}

class UrzaLordHighArtificerEffect extends OneShotEffect {

    UrzaLordHighArtificerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library, then exile the top card of your library. " +
                "Until end of turn, you may play that card without paying its mana cost";
    }

    private UrzaLordHighArtificerEffect(final UrzaLordHighArtificerEffect effect) {
        super(effect);
    }

    @Override
    public UrzaLordHighArtificerEffect copy() {
        return new UrzaLordHighArtificerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.shuffleLibrary(source, game);
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(
                controller.getId().toString()
                        + "-" + game.getState().getTurnNum()
                        + "-" + UrzaLordHighArtificer.class.toString(), game
        );
        String exileName = "Urza, Lord High Artificer free cast on "
                + game.getState().getTurnNum() + " turn for " + controller.getName();
        game.getExile().createZone(exileId, exileName).setCleanupOnEndTurn(true);
        if (!controller.moveCardsToExile(card, source, game, true, exileId, exileName)) {
            return false;
        }
        ContinuousEffect effect = new UrzaLordHighArtificerFromExileEffect();
        effect.setTargetPointer(new FixedTargets(game.getExile().getExileZone(exileId).getCards(game), game));
        game.addEffect(effect, source);
        return true;
    }
}

class UrzaLordHighArtificerFromExileEffect extends AsThoughEffectImpl {

    UrzaLordHighArtificerFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play that card without paying its mana cost";
    }

    private UrzaLordHighArtificerFromExileEffect(final UrzaLordHighArtificerFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UrzaLordHighArtificerFromExileEffect copy() {
        return new UrzaLordHighArtificerFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!affectedControllerId.equals(source.getControllerId())
                || !getTargetPointer().getTargets(game, source).contains(objectId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand() || card.getSpellAbility().getCosts() == null) {
            return true;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }
        player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
        return true;
    }
}
