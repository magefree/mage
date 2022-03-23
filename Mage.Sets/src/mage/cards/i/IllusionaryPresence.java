package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.MountainwalkAbility;
import mage.abilities.keyword.PlainswalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public final class IllusionaryPresence extends CardImpl {

    public IllusionaryPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cumulative upkeep {U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{U}")));

        // At the beginning of your upkeep, choose a land type. Illusionary Presence gains landwalk of the chosen type until end of turn.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ChooseBasicLandTypeEffect(Outcome.Neutral), TargetController.YOU, false);
        ability.addEffect(new IllusionaryPresenceEffect());
        this.addAbility(ability);

    }

    private IllusionaryPresence(final IllusionaryPresence card) {
        super(card);
    }

    @Override
    public IllusionaryPresence copy() {
        return new IllusionaryPresence(this);
    }
}

class IllusionaryPresenceEffect extends OneShotEffect {

    Ability gainedAbility;

    public IllusionaryPresenceEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} gains landwalk of the chosen type until end of turn";
    }

    public IllusionaryPresenceEffect(final IllusionaryPresenceEffect effect) {
        super(effect);
    }

    @Override
    public IllusionaryPresenceEffect copy() {
        return new IllusionaryPresenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            SubType landTypeChoice = SubType.byDescription((String) game.getState().getValue(mageObject.getId().toString() + "BasicLandType"));
            if (landTypeChoice != null) {
                switch (landTypeChoice) {
                    case PLAINS:
                        gainedAbility = new PlainswalkAbility();
                        break;
                    case FOREST:
                        gainedAbility = new ForestwalkAbility();
                        break;
                    case SWAMP:
                        gainedAbility = new SwampwalkAbility();
                        break;
                    case ISLAND:
                        gainedAbility = new IslandwalkAbility();
                        break;
                    case MOUNTAIN:
                        gainedAbility = new MountainwalkAbility();
                        break;
                }
                if (gainedAbility != null) {
                    GainAbilitySourceEffect effect = new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn);
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }
}
