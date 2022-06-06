
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class MistformWakecaster extends CardImpl {

    public MistformWakecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Mistform Wakecaster becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesChosenCreatureTypeSourceEffect(), new ManaCostsImpl<>("{1}")));

        // {2}{U}{U}, {T}: Choose a creature type. Each creature you control becomes that type until end of turn.
        Ability ability = new SimpleActivatedAbility(new BecomesChosenCreatureTypeControlledEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MistformWakecaster(final MistformWakecaster card) {
        super(card);
    }

    @Override
    public MistformWakecaster copy() {
        return new MistformWakecaster(this);
    }
}

class BecomesChosenCreatureTypeControlledEffect extends OneShotEffect {

    public BecomesChosenCreatureTypeControlledEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose a creature type. Each creature you control becomes that type until end of turn";
    }

    public BecomesChosenCreatureTypeControlledEffect(final BecomesChosenCreatureTypeControlledEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        String chosenType = "";
        if (player != null && card != null) {
            Choice typeChoice = new ChoiceCreatureType();
            String msg = "Choose a creature type";
            typeChoice.setMessage(msg);
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(card.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            chosenType = typeChoice.getChoice();
            if (chosenType != null && !chosenType.isEmpty()) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                    ContinuousEffect effect = new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.byDescription(chosenType));
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
                return true;
            }

        }
        return false;
    }

    @Override
    public Effect copy() {
        return new BecomesChosenCreatureTypeControlledEffect(this);
    }

}
