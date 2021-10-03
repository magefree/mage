
package mage.cards.h;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class HordeOfNotions extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elemental card from your graveyard");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public HordeOfNotions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {W}{U}{B}{R}{G}: You may play target Elemental card from your graveyard without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HordeOfNotionsEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private HordeOfNotions(final HordeOfNotions card) {
        super(card);
    }

    @Override
    public HordeOfNotions copy() {
        return new HordeOfNotions(this);
    }
}

class HordeOfNotionsEffect extends OneShotEffect {

    public HordeOfNotionsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may play target Elemental card from your graveyard without paying its mana cost";
    }

    public HordeOfNotionsEffect(final HordeOfNotionsEffect effect) {
        super(effect);
    }

    @Override
    public HordeOfNotionsEffect copy() {
        return new HordeOfNotionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null && controller.chooseUse(outcome, "Play " + card.getName() + " from your graveyard for free?", source, game)) {
                controller.playCard(card, game, true, new ApprovingObject(source, game));
            }
            return true;
        }
        return false;
    }
}
