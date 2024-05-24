package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KellanTheKid extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public KellanTheKid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast a spell from anywhere other than your hand, you may cast a permanent spell with equal or lesser mana value from your hand without paying its mana cost. If you don't, you may put a land card from your hand onto the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(new KellanTheKidEffect(), filter, false));
    }

    private KellanTheKid(final KellanTheKid card) {
        super(card);
    }

    @Override
    public KellanTheKid copy() {
        return new KellanTheKid(this);
    }
}

class KellanTheKidEffect extends OneShotEffect {

    KellanTheKidEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a permanent spell with equal or lesser mana value from your hand without " +
                "paying its mana cost. If you don't, you may put a land card from your hand onto the battlefield";
    }

    private KellanTheKidEffect(final KellanTheKidEffect effect) {
        super(effect);
    }

    @Override
    public KellanTheKidEffect copy() {
        return new KellanTheKidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        FilterCard filter = new FilterPermanentCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, spell.getManaValue() + 1));
        return CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(player.getHand()), filter)
                || new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND).apply(game, source);
    }
}
