package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public final class ArtilleryEnthusiast extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("modified creatures you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public ArtilleryEnthusiast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, filter)));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArtilleryEnthusiastEffect()));
    }

    private ArtilleryEnthusiast(final ArtilleryEnthusiast card) {
        super(card);
    }

    @Override
    public ArtilleryEnthusiast copy() {
        return new ArtilleryEnthusiast(this);
    }
}

class ArtilleryEnthusiastEffect extends OneShotEffect {

    ArtilleryEnthusiastEffect() {
        super(Outcome.Benefit);
        staticText = "you may discard a card. If you do, seek a card with mana value equal to that card's mana value";
    }

    private ArtilleryEnthusiastEffect(final ArtilleryEnthusiastEffect effect) {
        super(effect);
    }

    @Override
    public ArtilleryEnthusiastEffect copy() {
        return new ArtilleryEnthusiastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(outcome, "Discard a card?", source, game)) {
            return false;
        }
        Card discarded = player.discard(1, false, false, source, game).getCards(game).stream().findFirst().orElse(null);
        if (discarded == null) {
            return false;
        }
        FilterCard seekFilter = new FilterCard();
        seekFilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, discarded.getManaValue()));
        return player.seekCard(seekFilter, source, game);
    }
}
