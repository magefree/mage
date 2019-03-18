package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.XCMCGraveyardAdjuster;

/**
 * @author nantuko
 */
public final class GethLordOfTheVault extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or creature card with converted mana cost X from an opponent's graveyard");

    static {
        filter.add(new OwnerPredicate(TargetController.OPPONENT));
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public GethLordOfTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // {X}{B}: Put target artifact or creature card with converted mana cost X from an opponent's graveyard onto the battlefield under your control tapped.
        // Then that player puts the top X cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GethLordOfTheVaultEffect(), new ManaCostsImpl("{X}{B}"));
        ability.setTargetAdjuster(XCMCGraveyardAdjuster.instance);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    public GethLordOfTheVault(final GethLordOfTheVault card) {
        super(card);
    }

    @Override
    public GethLordOfTheVault copy() {
        return new GethLordOfTheVault(this);
    }

}

class GethLordOfTheVaultEffect extends OneShotEffect {

    public GethLordOfTheVaultEffect() {
        super(Outcome.Benefit);
        staticText = "Put target artifact or creature card with converted mana cost X from an opponent's graveyard onto the battlefield under your control tapped. Then that player puts the top X cards of their library into their graveyard";
    }

    public GethLordOfTheVaultEffect(final GethLordOfTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                Player player = game.getPlayer(card.getOwnerId());
                if (player != null) {
                    player.moveCards(player.getLibrary().getTopCards(game, card.getConvertedManaCost()), Zone.GRAVEYARD, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GethLordOfTheVaultEffect copy() {
        return new GethLordOfTheVaultEffect(this);
    }

}
