package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.KarnConstructToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaLordHighArtificer extends CardImpl {

    public UrzaLordHighArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Urza, Lord High Artificer enters the battlefield, create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KarnConstructToken()))
                .addHint(ArtifactYouControlHint.instance)
        );

        // Tap an untapped artifact you control: Add {U}.
        FilterControlledPermanent filter = new FilterControlledArtifactPermanent("untapped artifact you control");
        filter.add(TappedPredicate.UNTAPPED);
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new UrzaLordHighArtificerManaEffect(filter),
                new TapTargetCost(new TargetControlledPermanent(filter))));

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
        this.staticText = "Shuffle your library, then exile the top card. " +
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
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        controller.shuffleLibrary(source, game);
        Card card = controller.getLibrary().getFromTop(game);
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, card,
                TargetController.YOU, Duration.EndOfTurn, true, false, false);
    }
}

class UrzaLordHighArtificerManaEffect extends BasicManaEffect {

    private final FilterPermanent filter;

    public UrzaLordHighArtificerManaEffect(FilterPermanent filter) {
        super(Mana.BlueMana(1));
        this.filter = filter;
    }

    public UrzaLordHighArtificerManaEffect(final UrzaLordHighArtificerManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public UrzaLordHighArtificerManaEffect copy() {
        return new UrzaLordHighArtificerManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            List<Mana> netMana = new ArrayList<>();
            if (count > 0) {
                netMana.add(Mana.BlueMana(count));
            }
            return netMana;
        }
        return super.getNetMana(game, source);
    }

}
