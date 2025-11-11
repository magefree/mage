package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoilingRockRioter extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ALLY, "untaped Ally you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BoilingRockRioter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Firebending 1
        this.addAbility(new FirebendingAbility(1));

        // Tap an untapped Ally you control: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new TapTargetCost(filter));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Whenever this creature attacks, you may cast an Ally spell from among cards you own exiled with this creature.
        this.addAbility(new AttacksTriggeredAbility(new BoilingRockRioterEffect()));
    }

    private BoilingRockRioter(final BoilingRockRioter card) {
        super(card);
    }

    @Override
    public BoilingRockRioter copy() {
        return new BoilingRockRioter(this);
    }
}

class BoilingRockRioterEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(SubType.ALLY);

    BoilingRockRioterEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast an Ally spell from among cards you own exiled with this creature";
    }

    private BoilingRockRioterEffect(final BoilingRockRioterEffect effect) {
        super(effect);
    }

    @Override
    public BoilingRockRioterEffect copy() {
        return new BoilingRockRioterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = Optional
                .ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getExile()::getExileZone)
                .map(CardsImpl::new)
                .orElseGet(CardsImpl::new);
        cards.removeIf(uuid -> !source.isControlledBy(game.getOwnerId(uuid)));
        return CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
    }
}
