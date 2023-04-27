
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
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
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

        // {2}{U}, {tap}: Draw a card. If you have nine or more cards in hand, flip Jushi Apprentice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new FlipSourceEffect(new TomoyaTheRevealer()), new CardsInHandCondition(ComparisonType.MORE_THAN, 8),
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

class TomoyaTheRevealer extends TokenImpl {

    TomoyaTheRevealer() {
        super("Tomoya the Revealer", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.WIZARD);
        power = new MageInt(2);
        toughness = new MageInt(3);

        // {3}{U}{U},{T} : Target player draws X cards, where X is the number of cards in your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(CardsInControllerHandCount.instance), new ManaCostsImpl<>("{3}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }
    public TomoyaTheRevealer(final TomoyaTheRevealer token) {
        super(token);
    }

    public TomoyaTheRevealer copy() {
        return new TomoyaTheRevealer(this);
    }
}
