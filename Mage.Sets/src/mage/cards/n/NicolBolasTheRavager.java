package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class NicolBolasTheRavager extends CardImpl {

    public NicolBolasTheRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = NicolBolasTheArisen.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nicol Bolas, the Ravager enters the battlefield, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(StaticValue.get(1), false, TargetController.OPPONENT)));

        // {4}{U}{B}{R}: Exile Nicol Bolas, the Ravager, then return him to the battlefield transformed under his owner's control. Activate this ability only any time you could cast a sorcerry.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileAndReturnTransformedSourceEffect(Pronoun.HE),
                new ManaCostsImpl("{4}{U}{B}{R}")
        ));
    }

    private NicolBolasTheRavager(final NicolBolasTheRavager card) {
        super(card);
    }

    @Override
    public NicolBolasTheRavager copy() {
        return new NicolBolasTheRavager(this);
    }
}
