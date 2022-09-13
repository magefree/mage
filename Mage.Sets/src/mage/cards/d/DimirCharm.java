
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class DimirCharm extends CardImpl {

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature with power 2 or less");
    private static final FilterSpell filterSorcery = new FilterSpell("sorcery spell");

    static {
        filterCreature.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filterSorcery.add(CardType.SORCERY.getPredicate());
    }

    public DimirCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        //Choose one - Counter target sorcery spell
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSorcery));

        //or destroy target creature with power 2 or less
        Mode mode1 = new Mode(new DestroyTargetEffect());
        mode1.addTarget(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().addMode(mode1);

        //or look at the top three cards of target player's library, then put one back and the rest into that player's graveyard
        Mode mode2 = new Mode(new DimirCharmEffect());
        mode2.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode2);
    }

    private DimirCharm(final DimirCharm card) {
        super(card);
    }

    @Override
    public DimirCharm copy() {
        return new DimirCharm(this);
    }
}

class DimirCharmEffect extends OneShotEffect {

    public DimirCharmEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top three cards of target player's library, then put one back and the rest into that player's graveyard";
    }

    public DimirCharmEffect(final DimirCharmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && player != null) {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("Card to put back on top of library"));
                if (controller.chooseTarget(Outcome.Benefit, cards, target, source, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DimirCharmEffect copy() {
        return new DimirCharmEffect(this);
    }
}
