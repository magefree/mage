package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FamishedWorldsire extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("land");

    public FamishedWorldsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // Devour land 3
        this.addAbility(new DevourAbility(3, filter));

        // When this creature enters, look at the top X cards of your library, where X is this creature's power. Put any number of land cards from among them onto the battlefield tapped, then shuffle.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FamishedWorldsireEffect());
        ability.addEffect(new ShuffleLibrarySourceEffect().setText(", then shuffle"));
        this.addAbility(ability);
    }

    private FamishedWorldsire(final FamishedWorldsire card) {
        super(card);
    }

    @Override
    public FamishedWorldsire copy() {
        return new FamishedWorldsire(this);
    }
}

class FamishedWorldsireEffect extends OneShotEffect {

    FamishedWorldsireEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "look at the top X cards of your library, where X is this creature's power. " +
                "Put any number of land cards from among them onto the battlefield tapped";
    }

    private FamishedWorldsireEffect(final FamishedWorldsireEffect effect) {
        super(effect);
    }

    @Override
    public FamishedWorldsireEffect copy() {
        return new FamishedWorldsireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = SourcePermanentPowerValue.NOT_NEGATIVE.calculate(game, source, this);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, amount));
        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS);
        target.withChooseHint("to put onto the battlefield tapped");
        player.choose(outcome, cards, target, source, game);
        player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
        return true;
    }
}
