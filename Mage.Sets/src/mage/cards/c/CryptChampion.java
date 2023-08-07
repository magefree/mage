package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public final class CryptChampion extends CardImpl {

    public CryptChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Crypt Champion enters the battlefield, each player puts a creature card with converted mana cost 3 or less from their graveyard onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CryptChampionEffect()));

        // When Crypt Champion enters the battlefield, sacrifice it unless {R} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(ManaWasSpentCondition.RED), false));
    }

    private CryptChampion(final CryptChampion card) {
        super(card);
    }

    @Override
    public CryptChampion copy() {
        return new CryptChampion(this);
    }
}

class CryptChampionEffect extends OneShotEffect {

    CryptChampionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player puts a creature card with mana value 3 or less from their graveyard onto the battlefield";
    }

    CryptChampionEffect(final CryptChampionEffect effect) {
        super(effect);
    }

    @Override
    public CryptChampionEffect copy() {
        return new CryptChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");
                    filter.add(new OwnerIdPredicate(playerId));
                    filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
                    Target target = new TargetCardInGraveyard(filter);
                    if (target.canChoose(playerId, source, game)
                            && player.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            toBattlefield.add(card);
                        }
                    }
                }
            }

            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
            
        }
        return false;
    }
}
