
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class TreacherousVampire extends CardImpl {

    public TreacherousVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Treacherous Vampire attacks or blocks, sacrifice it unless you exile a card from your graveyard.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new DoUnlessControllerPaysEffect(
                        new SacrificeSourceEffect(),
                        new ExileFromGraveCost(new ExileFromGraveCost(new TargetCardInYourGraveyard()))
                ), false
        ));

        // Threshold - As long as seven or more cards are in your graveyard, Treacherous Vampire gets +2/+2 and has "When Treacherous Vampire dies, you lose 6 life."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), new CardsInControllerGraveCondition(7),
                "As long as seven or more cards are in your graveyard, {this} gets +2/+2"));
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(new LoseLifeSourceControllerEffect(6))),
                new CardsInControllerGraveCondition(7), "and has \"When {this} dies, you lose 6 life.\""
        );
        ability.addEffect(effect);
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public TreacherousVampire(final TreacherousVampire card) {
        super(card);
    }

    @Override
    public TreacherousVampire copy() {
        return new TreacherousVampire(this);
    }
}
