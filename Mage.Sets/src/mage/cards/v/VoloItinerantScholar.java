package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.VolosJournalToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoloItinerantScholar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanent you control named Volo's Journal");

    static {
        filter.add(new NamePredicate("Volo's Journal"));
    }

    public VoloItinerantScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Volo enters the battlefield, create Volo's Journal, a legendary colorless artifact token with hexproof and "Whenever you cast a creature spell, note one of its creature types that hasn't been noted for this artifact."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new VolosJournalToken())));

        // {2}, {T}: Draw a card for each creature type noted for target permanent you control named Volo's Journal.
        Ability ability = new SimpleActivatedAbility(new VoloItinerantScholarEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private VoloItinerantScholar(final VoloItinerantScholar card) {
        super(card);
    }

    @Override
    public VoloItinerantScholar copy() {
        return new VoloItinerantScholar(this);
    }
}

class VoloItinerantScholarEffect extends OneShotEffect {

    VoloItinerantScholarEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each creature type noted for target permanent you control named Volo's Journal";
    }

    private VoloItinerantScholarEffect(final VoloItinerantScholarEffect effect) {
        super(effect);
    }

    @Override
    public VoloItinerantScholarEffect copy() {
        return new VoloItinerantScholarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        return player.drawCards(
                VolosJournalToken.getNotedTypes(game, permanent).size(),
                source,
                game) > 0;
    }
}
