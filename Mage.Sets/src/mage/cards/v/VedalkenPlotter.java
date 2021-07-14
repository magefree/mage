
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class VedalkenPlotter extends CardImpl {
    
    private static final String rule = "exchange control of target land you control and target land an opponent controls";
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("land an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VedalkenPlotter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Vedalken Plotter enters the battlefield, exchange control of target land you control and target land an opponent controls.
        Effect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true);
        effect.setText("exchange control of target land you control and target land an opponent controls");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VedalkenPlotter(final VedalkenPlotter card) {
        super(card);
    }

    @Override
    public VedalkenPlotter copy() {
        return new VedalkenPlotter(this);
    }
}
