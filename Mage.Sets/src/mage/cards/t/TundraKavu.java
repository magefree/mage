
package mage.cards.t;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class TundraKavu extends CardImpl {

    public TundraKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target land becomes a Plains or an Island until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TundraKavuEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private TundraKavu(final TundraKavu card) {
        super(card);
    }

    @Override
    public TundraKavu copy() {
        return new TundraKavu(this);
    }
}

class TundraKavuEffect extends BecomesBasicLandTargetEffect {

    public TundraKavuEffect() {
        super(Duration.EndOfTurn);
        staticText = "Target land becomes a Plains or an Island until end of turn.";
    }

    public TundraKavuEffect(final TundraKavuEffect effect) {
        super(effect);
    }

    @Override
    public TundraKavuEffect copy() {
        return new TundraKavuEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        landTypes.clear();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<String> choiceSet = new LinkedHashSet<>();
            choiceSet.add("Island");
            choiceSet.add("Plains");
            ChoiceImpl choice = new ChoiceImpl(true, ChoiceHintType.CARD);
            choice.setChoices(choiceSet);
            choice.setMessage("Choose a basic land type");
            if (!controller.choose(outcome, choice, game)) {
                discard();
                return;
            }
            landTypes.add(SubType.byDescription(choice.getChoice()));
        } else {
            this.discard();
        }

        super.init(source, game);
    }
}
