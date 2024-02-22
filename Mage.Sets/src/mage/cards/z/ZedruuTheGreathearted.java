package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsYouOwnThatOpponentsControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TargetPlayerGainControlTargetPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author andyfries
 */
public final class ZedruuTheGreathearted extends CardImpl {

    public ZedruuTheGreathearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR, SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain X life and draw X cards, where X is the number of permanents you own that your opponents control.
        Effect effect = new GainLifeEffect(PermanentsYouOwnThatOpponentsControlCount.instance);
        effect.setText("you gain X life");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        effect = new DrawCardSourceControllerEffect(PermanentsYouOwnThatOpponentsControlCount.instance);
        effect.setText("and draw X cards, where X is the number of permanents you own that your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {R}{W}{U}: Target opponent gains control of target permanent you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TargetPlayerGainControlTargetPermanentEffect(), new ManaCostsImpl<>("{U}{R}{W}"));
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private ZedruuTheGreathearted(final ZedruuTheGreathearted card) {
        super(card);
    }

    @Override
    public ZedruuTheGreathearted copy() {
        return new ZedruuTheGreathearted(this);
    }

}
