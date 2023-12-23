package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlquistProftMasterSleuth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.CLUE, "a Clue");

    public AlquistProftMasterSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Alquist Proft, Master Sleuth enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect()));

        // {X}{W}{U}{U}, {T}, Sacrifice a Clue: You draw X cards and gain X life.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR, "you"), new ManaCostsImpl<>("{X}{W}{U}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR).setText("and gain X life"));
        this.addAbility(ability);
    }

    private AlquistProftMasterSleuth(final AlquistProftMasterSleuth card) {
        super(card);
    }

    @Override
    public AlquistProftMasterSleuth copy() {
        return new AlquistProftMasterSleuth(this);
    }
}
