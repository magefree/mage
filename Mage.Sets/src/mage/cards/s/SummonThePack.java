
package mage.cards.s;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseExpansionSetEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class SummonThePack extends CardImpl {

    public SummonThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{B}");

        // Open a sealed Magic booster pack, reveal the cards, and put all creature cards revealed this way onto the battlefield under your control. They're Zombies in addition to their other types. (Remove those cards from your deck before beginning a new game)
        this.getSpellAbility().addEffect(new SummonThePackEffect());
    }

    private SummonThePack(final SummonThePack card) {
        super(card);
    }

    @Override
    public SummonThePack copy() {
        return new SummonThePack(this);
    }
}

class SummonThePackEffect extends OneShotEffect {

    public SummonThePackEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Open a sealed Magic booster pack, reveal the cards, and put all creature cards revealed this way onto the battlefield under your control. They're Zombies in addition to their other types";
    }

    public SummonThePackEffect(final SummonThePackEffect effect) {
        super(effect);
    }

    @Override
    public SummonThePackEffect copy() {
        return new SummonThePackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ChooseExpansionSetEffect effect = new ChooseExpansionSetEffect(Outcome.UnboostCreature);
        effect.apply(game, source);
        Player controller = game.getPlayer(source.getControllerId());

        String setChosen = null;
        if (effect.getValue("setchosen") != null) {
            setChosen = (String) effect.getValue("setchosen");
        } else if (game.getState().getValue(this.getId() + "_set") != null) {
            setChosen = (String) game.getState().getValue(this.getId() + "_set");
        }

        if (setChosen != null && controller != null) {
            //ExpansionInfo set = ExpansionRepository.instance.getSetByName(setChosen);
            ExpansionSet expansionSet = Sets.findSet(setChosen);
            if (expansionSet != null) {
                List<Card> boosterPack = expansionSet.create15CardBooster();
                List<Card> creatureCards = new ArrayList<>();

                if (boosterPack != null) {
                    StringBuilder message = new StringBuilder(controller.getLogName()).append(" opened: ");

                    for (Card c : boosterPack) {
                        if (c != null && c.isCreature(game)) {
                            message.append(c.getName()).append(" ");
                            message.append(" (creature card) ");
                            ContinuousEffect effect2 = new AddCreatureTypeAdditionEffect(SubType.ZOMBIE, false);
                            effect2.setTargetPointer(new FixedTarget(c.getId()));
                            game.addEffect(effect2, source);
                            creatureCards.add(c);
                            c.setZone(Zone.OUTSIDE, game);
                        }
                    }

                    if (creatureCards.size() > 0) {
                        Set<Card> ccs = new HashSet<>(creatureCards);
                        game.loadCards(ccs, controller.getId());
                        controller.moveCards(ccs, Zone.BATTLEFIELD, source, game);
                    }

                    game.informPlayers(message.toString());
                }
            }
        }

        return false;
    }
}
