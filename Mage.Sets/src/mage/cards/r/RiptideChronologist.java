
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author fireshoes
 */
public final class RiptideChronologist extends CardImpl {

    public RiptideChronologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}, Sacrifice Riptide Chronologist: Untap all creatures of the creature type of your choice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideChronologistEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RiptideChronologist(final RiptideChronologist card) {
        super(card);
    }

    @Override
    public RiptideChronologist copy() {
        return new RiptideChronologist(this);
    }
}

class RiptideChronologistEffect extends OneShotEffect {

    public RiptideChronologistEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Untap all creatures of the creature type of your choice";
    }

    public RiptideChronologistEffect(final RiptideChronologistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            Choice typeChoice = new ChoiceCreatureType(sourceObject);
            if (player.choose(outcome, typeChoice, game)) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
                FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
                filterCreaturePermanent.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
                for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
                    creature.untap(game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RiptideChronologistEffect copy() {
        return new RiptideChronologistEffect(this);
    }
}
