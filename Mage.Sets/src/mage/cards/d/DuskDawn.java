
package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;

public final class DuskDawn extends SplitCard {

    private static final FilterCreaturePermanent filterCreatures3orGreater = new FilterCreaturePermanent("creatures with power greater than or equal to 3");

    static {
        filterCreatures3orGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public DuskDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{2}{W}{W}", "{3}{W}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Dusk
        // Destroy all creatures with power 3 or greater.
        Effect destroy = new DestroyAllEffect(filterCreatures3orGreater);
        destroy.setText("Destroy all creatures with power greater than or equal to 3.");
        getLeftHalfCard().getSpellAbility().addEffect(destroy);

        // Dawn
        // Return all creature cards with power less than or equal to 2 from your graveyard to your hand.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new DawnEffect());

    }

    private DuskDawn(final DuskDawn card) {
        super(card);
    }

    @Override
    public DuskDawn copy() {
        return new DuskDawn(this);
    }
}

class DawnEffect extends OneShotEffect {

    private static final FilterCard filter2orLess = new FilterCreatureCard("creatures with power less than or equal to 2");

    static {
        filter2orLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    DawnEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return all creature cards with power 2 or less from your graveyard to your hand.";
    }

    DawnEffect(final DawnEffect effect) {
        super(effect);
    }

    @Override
    public DawnEffect copy() {
        return new DawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Set<Card> cards = player.getGraveyard().getCards(filter2orLess, game);
            player.moveCards(cards, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
