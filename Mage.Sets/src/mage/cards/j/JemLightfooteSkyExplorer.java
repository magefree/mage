package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JemLightfooteSkyExplorer extends CardImpl {

    public JemLightfooteSkyExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                TargetController.YOU, HaventCastSpellFromHandThisTurnCondition.instance, false
        ).addHint(HaventCastSpellFromHandThisTurnCondition.hint));
    }

    private JemLightfooteSkyExplorer(final JemLightfooteSkyExplorer card) {
        super(card);
    }

    @Override
    public JemLightfooteSkyExplorer copy() {
        return new JemLightfooteSkyExplorer(this);
    }
}
