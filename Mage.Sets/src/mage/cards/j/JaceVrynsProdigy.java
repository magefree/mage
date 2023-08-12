
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Pronoun;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class JaceVrynsProdigy extends CardImpl {

    public JaceVrynsProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.j.JaceTelepathUnbound.class;

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1), new TapSourceCost());
        Effect effect = new ConditionalOneShotEffect(new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED,Pronoun.HE), new CardsInControllerGraveyardCondition(5));
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private JaceVrynsProdigy(final JaceVrynsProdigy card) {
        super(card);
    }

    @Override
    public JaceVrynsProdigy copy() {
        return new JaceVrynsProdigy(this);
    }
}
