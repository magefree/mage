package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlamewakePhoenix extends CardImpl {

    public FlamewakePhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Flamewake Phoenix attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // <i>Ferocious</i> &mdash; At the beginning of combat on your turn, if you control a creature with power 4 or greater, you may pay {R}. If you do, return Flamewake Phoenix from your graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        Zone.GRAVEYARD,
                        new DoIfCostPaid(new ReturnToBattlefieldUnderOwnerControlSourceEffect(), new ManaCostsImpl<>("{R}")),
                        TargetController.YOU, false, false),
                FerociousCondition.instance,
                "<i>Ferocious</i> &mdash; At the beginning of combat on your turn, if you control a creature with power 4 or greater, you may pay {R}. If you do, return {this} from your graveyard to the battlefield."
        ).addHint(FerociousHint.instance));
    }

    private FlamewakePhoenix(final FlamewakePhoenix card) {
        super(card);
    }

    @Override
    public FlamewakePhoenix copy() {
        return new FlamewakePhoenix(this);
    }
}
