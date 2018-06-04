
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author JayDi85
 */
public final class DuskLegionZealot  extends CardImpl {

    public DuskLegionZealot (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Dusk Legion Zealot enters the battlefield, you draw a card and you lose 1 life.
        Effect drawEffect = new DrawCardSourceControllerEffect(1);
        drawEffect.setText("you draw a card");
        Ability ability = new EntersBattlefieldTriggeredAbility(drawEffect);
        Effect lifeEffect = new LoseLifeSourceControllerEffect(1);
        lifeEffect.setText("and you lose 1 life");
        ability.addEffect(lifeEffect);
        this.addAbility(ability);
    }

    public DuskLegionZealot (final DuskLegionZealot  card) {
        super(card);
    }

    @Override
    public DuskLegionZealot  copy() {
        return new DuskLegionZealot (this);
    }
}