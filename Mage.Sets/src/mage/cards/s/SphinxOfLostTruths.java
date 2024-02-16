
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SphinxOfLostTruths extends CardImpl {

    public SphinxOfLostTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Kicker {1}{U} (You may pay an additional {1}{U} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sphinx of Lost Truths enters the battlefield, draw three cards. Then if it wasn't kicked, discard three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3));
        ability.addEffect(new ConditionalOneShotEffect(new DiscardControllerEffect(3), new InvertCondition(KickedCondition.ONCE),
                "Then if it wasn't kicked, discard three cards"));
        this.addAbility(ability);
    }

    private SphinxOfLostTruths(final SphinxOfLostTruths card) {
        super(card);
    }

    @Override
    public SphinxOfLostTruths copy() {
        return new SphinxOfLostTruths(this);
    }
}