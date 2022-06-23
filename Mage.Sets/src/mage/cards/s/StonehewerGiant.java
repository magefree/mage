
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StonehewerGiant extends CardImpl {

    public StonehewerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {1}{W}, {tap}: Search your library for an Equipment card and put it onto the battlefield. Attach it to a creature you control. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new StonehewerGiantEffect(),
                new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private StonehewerGiant(final StonehewerGiant card) {
        super(card);
    }

    @Override
    public StonehewerGiant copy() {
        return new StonehewerGiant(this);
    }
}

class StonehewerGiantEffect extends OneShotEffect {

    public StonehewerGiantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "search your library for an Equipment card, put it onto the battlefield, attach it to a creature you control, then shuffle";
    }

    public StonehewerGiantEffect(final StonehewerGiantEffect effect) {
        super(effect);
    }

    @Override
    public StonehewerGiantEffect copy() {
        return new StonehewerGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Equipment");
        filter.add(SubType.EQUIPMENT.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                Permanent equipment = game.getPermanent(card.getId());
                Target targetCreature = new TargetControlledCreaturePermanent();
                targetCreature.setNotTarget(true);
                if (equipment != null && controller.choose(Outcome.BoostCreature, targetCreature, source, game)) {
                    Permanent permanent = game.getPermanent(targetCreature.getFirstTarget());
                    permanent.addAttachment(equipment.getId(), source, game);
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
