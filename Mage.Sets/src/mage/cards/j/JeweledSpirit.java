
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColorOrArtifact;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class JeweledSpirit extends CardImpl {

    public JeweledSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice two lands: Jeweled Spirit gains protection from artifacts or from the color of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new JeweledSpiritEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true))));
    }

    private JeweledSpirit(final JeweledSpirit card) {
        super(card);
    }

    @Override
    public JeweledSpirit copy() {
        return new JeweledSpirit(this);
    }
}

class JeweledSpiritEffect extends OneShotEffect {

    public JeweledSpiritEffect() {
        super(Outcome.AddAbility);
        this.staticText = "{this} gains protection from artifacts or from the color of your choice until end of turn";
    }

    public JeweledSpiritEffect(final JeweledSpiritEffect effect) {
        super(effect);
    }

    @Override
    public JeweledSpiritEffect copy() {
        return new JeweledSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColorOrArtifact choice = new ChoiceColorOrArtifact();
        if (controller != null && controller.choose(outcome, choice, game)) {
            FilterCard protectionFilter = new FilterCard();
            if (choice.isArtifactSelected()) {
                protectionFilter.add(CardType.ARTIFACT.getPredicate());
            } else {
                protectionFilter.add(new ColorPredicate(choice.getColor()));
            }
            protectionFilter.setMessage(choice.getChoice());
            ProtectionAbility protectionAbility = new ProtectionAbility(protectionFilter);
            ContinuousEffect effect = new GainAbilitySourceEffect(protectionAbility, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
