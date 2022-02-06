package mage.cards.f;

import java.util.StringTokenizer;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public final class FaerieArtisans extends CardImpl {

    private static final FilterCreaturePermanent filterNontoken = new FilterCreaturePermanent("nontoken creature");

    static {
        filterNontoken.add(TokenPredicate.FALSE);
        filterNontoken.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public FaerieArtisans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a nontoken creature enters the battlefield under an opponent's control, create a token that's a copy of that creature except it's an artifact in addition to its other types. Then exile all other tokens created with Faerie Artisans.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new FaerieArtisansEffect(), filterNontoken, false, SetTargetPointer.PERMANENT,
                "Whenever a nontoken creature enters the battlefield under an opponent's control, create a token that's a copy of that creature except it's an artifact in addition to its other types. Then exile all other tokens created with {this}.");
        this.addAbility(ability);
    }

    private FaerieArtisans(final FaerieArtisans card) {
        super(card);
    }

    @Override
    public FaerieArtisans copy() {
        return new FaerieArtisans(this);
    }
}

class FaerieArtisansEffect extends OneShotEffect {

    public FaerieArtisansEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature except it's an artifact in addition to its other types. "
                + "Then exile all other tokens created with {this}";
    }

    public FaerieArtisansEffect(final FaerieArtisansEffect effect) {
        super(effect);
    }

    @Override
    public FaerieArtisansEffect copy() {
        return new FaerieArtisansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanentToCopy = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanentToCopy != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, CardType.ARTIFACT, false);
            effect.setTargetPointer(new FixedTarget(permanentToCopy, game));
            if (effect.apply(game, source)) {
                String oldTokens = (String) game.getState().getValue(source.getSourceId().toString() + source.getSourceObjectZoneChangeCounter());
                StringBuilder sb = new StringBuilder();
                for (Permanent permanent : effect.getAddedPermanents()) {
                    if (sb.length() > 0) {
                        sb.append(';');
                    }
                    sb.append(permanent.getId());
                }
                game.getState().setValue(source.getSourceId().toString() + source.getSourceObjectZoneChangeCounter(), sb.toString());

                if (oldTokens != null) {
                    Cards cards = new CardsImpl();
                    StringTokenizer tokenizer = new StringTokenizer(oldTokens, ";");
                    while (tokenizer.hasMoreTokens()) {
                        String tokenId = tokenizer.nextToken();
                        cards.add(UUID.fromString(tokenId));
                    }
                    controller.moveCards(cards, Zone.EXILED, source, game);
                }
                return true;
            }
        }

        return false;
    }
}
