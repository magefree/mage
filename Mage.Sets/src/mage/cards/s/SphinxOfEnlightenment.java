package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfEnlightenment extends CardImpl {

    public SphinxOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sphinx of Enlightenment enters the battlefield, target opponent draws a card and you draw three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardTargetEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy("and you"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SphinxOfEnlightenment(final SphinxOfEnlightenment card) {
        super(card);
    }

    @Override
    public SphinxOfEnlightenment copy() {
        return new SphinxOfEnlightenment(this);
    }
}
