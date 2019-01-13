
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class GiantSlug extends CardImpl {
     
    public GiantSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {5}: At the beginning of your next upkeep, choose a basic land type. Giant Slug gains landwalk of the chosen type until the end of that turn.
        AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility ability = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new GiantSlugEffect());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(ability), new ManaCostsImpl("{5}")));
    }

    public GiantSlug(final GiantSlug card) {
        super(card);
    }

    @Override
    public GiantSlug copy() {
        return new GiantSlug(this);
    }

}

class GiantSlugEffect extends OneShotEffect {

    public GiantSlugEffect() {
        super(Outcome.AddAbility);
        this.staticText = "At the beginning of your next upkeep, choose a basic land type. {this} gains landwalk of the chosen type until the end of that turn";
    }

    public GiantSlugEffect(final GiantSlugEffect effect) {
        super(effect);
    }

    @Override
    public GiantSlugEffect copy() {
        return new GiantSlugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            ChoiceImpl choices = new ChoiceBasicLandType();
            if (controller.choose(outcome, choices, game)) {
                game.informPlayers(sourcePermanent.getName() + ":  Chosen basic land type is " + choices.getChoice());
                FilterLandPermanent filter = new FilterLandPermanent(choices.getChoice());
                if (choices.getChoice().equals("Plains")) {
                    filter.add(new SubtypePredicate(SubType.PLAINS));
                }
                if (choices.getChoice().equals("Island")) {
                    filter.add(new SubtypePredicate(SubType.ISLAND));
                }
                if (choices.getChoice().equals("Swamp")) {
                    filter.add(new SubtypePredicate(SubType.SWAMP));
                }
                if (choices.getChoice().equals("Mountain")) {
                    filter.add(new SubtypePredicate(SubType.MOUNTAIN));
                }
                if (choices.getChoice().equals("Forest")) {
                    filter.add(new SubtypePredicate(SubType.FOREST));
                }
                Ability landwalkAbility = new LandwalkAbility(filter);
                game.addEffect(new GainAbilitySourceEffect(landwalkAbility, Duration.EndOfTurn, false), source);
                return true;
            }
        }
        return false;
    }
}
