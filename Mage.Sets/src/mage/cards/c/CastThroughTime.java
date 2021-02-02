package mage.cards.c;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author magenoxx_at_gmail.com
 */
public final class CastThroughTime extends CardImpl {

    protected static final FilterCard filter = new FilterCard("Instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public CastThroughTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");

        // Instant and sorcery spells you control have rebound.
        //  (Exile the spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast that card from exile without paying its mana cost.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainReboundEffect()));
    }

    private CastThroughTime(final CastThroughTime card) {
        super(card);
    }

    @Override
    public CastThroughTime copy() {
        return new CastThroughTime(this);
    }
}

class GainReboundEffect extends ContinuousEffectImpl {

    public GainReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Instant and sorcery spells you control have rebound  <i>(Exile the spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast that card from exile without paying its mana cost.)</i>";
    }

    public GainReboundEffect(final GainReboundEffect effect) {
        super(effect);
    }

    @Override
    public GainReboundEffect copy() {
        return new GainReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            for (Card card : player.getHand().getCards(CastThroughTime.filter, game)) {
                addReboundAbility(card, game);
            }
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    Card card = spell.getCard();
                    if (card != null) {
                        addReboundAbility(card, game);
                    }

                }
            }
            return true;
        }
        return false;
    }

    private void addReboundAbility(Card card, Game game) {
        if (CastThroughTime.filter.match(card, game)) {
            boolean found = card.getAbilities(game).containsClass(ReboundAbility.class);
            if (!found) {
                Ability ability = new ReboundAbility();
                game.getState().addOtherAbility(card, ability);
            }
        }
    }
}
