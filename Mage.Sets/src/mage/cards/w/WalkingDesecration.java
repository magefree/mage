
package mage.cards.w;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 * @author fireshoes
 */
public final class WalkingDesecration extends CardImpl {

    public WalkingDesecration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}: Creatures of the creature type of your choice attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WalkingDesecrationEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WalkingDesecration(final WalkingDesecration card) {
        super(card);
    }

    @Override
    public WalkingDesecration copy() {
        return new WalkingDesecration(this);
    }
}

class WalkingDesecrationEffect extends OneShotEffect {

    public WalkingDesecrationEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Creatures of the creature type of your choice attack this turn if able";
    }

    public WalkingDesecrationEffect(final WalkingDesecrationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null) {
            if (sourceObject != null) {
                Choice typeChoice = new ChoiceCreatureType(sourceObject);
                if (player.choose(outcome, typeChoice, game)) {
                    game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
                    RequirementEffect effect = new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn);
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public WalkingDesecrationEffect copy() {
        return new WalkingDesecrationEffect(this);
    }
}
