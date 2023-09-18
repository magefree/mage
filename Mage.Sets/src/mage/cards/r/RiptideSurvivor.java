
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RiptideSurvivor extends CardImpl {

    public RiptideSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {1}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{U}{U}")));
        // When Riptide Survivor is turned face up, discard two cards, then draw three cards.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DiscardControllerEffect(2));
        Effect effect = new DrawCardSourceControllerEffect(3);
        effect.setText("then draw three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RiptideSurvivor(final RiptideSurvivor card) {
        super(card);
    }

    @Override
    public RiptideSurvivor copy() {
        return new RiptideSurvivor(this);
    }
}

