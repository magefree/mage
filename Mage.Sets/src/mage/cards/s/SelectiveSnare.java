package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author TheElk801
 */
public final class SelectiveSnare extends CardImpl {

    public SelectiveSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // Return X target creatures of the creature type of your choice to their owner's hand.
        this.getSpellAbility().addEffect(
                new ReturnToHandTargetEffect()
                        .setText("Return X target creatures of "
                                + "the creature type of your choice "
                                + "to their owner's hand")
        );
        this.getSpellAbility().setTargetAdjuster(SelectiveSnareAdjuster.instance);
    }

    private SelectiveSnare(final SelectiveSnare card) {
        super(card);
    }

    @Override
    public SelectiveSnare copy() {
        return new SelectiveSnare(this);
    }
}

enum SelectiveSnareAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        Choice choice = new ChoiceCreatureType();
        if (!player.choose(Outcome.Benefit, choice, game)) {
            return;
        }
        SubType subType = SubType.byDescription(choice.getChoice());
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent(subType.toString() + " creatures");
        filter.add(subType.getPredicate());
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(xValue, filter));
    }
}
