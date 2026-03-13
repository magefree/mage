
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class JushiApprentice extends CardImpl {

    public JushiApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Tomoya the Revealer";

        Ability flipAbility = new SimpleActivatedAbility(new DrawCardTargetEffect(CardsInControllerHandCount.ANY), new ManaCostsImpl<>("{3}{U}{U}"));
        flipAbility.addCost(new TapSourceCost());
        flipAbility.addTarget(new TargetPlayer());

        CreatureToken flipToken = new CreatureToken(2, 3, "", SubType.HUMAN, SubType.WIZARD)
            .withName("Tomoya the Revealer")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("U")
            .withAbility(flipAbility);

        // {2}{U}, {tap}: Draw a card. If you have nine or more cards in hand, flip Jushi Apprentice.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new FlipSourceEffect(flipToken), new CardsInHandCondition(ComparisonType.MORE_THAN, 8),
                    "If you have nine or more cards in hand, flip {this}"));
        this.addAbility(ability);
    }

    private JushiApprentice(final JushiApprentice card) {
        super(card);
    }

    @Override
    public JushiApprentice copy() {
        return new JushiApprentice(this);
    }
}
