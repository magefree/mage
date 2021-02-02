
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class Cloudblazer extends CardImpl {

    public Cloudblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Cloudblazer enters the battlefield, you gain 2 life and draw two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText("and draw two cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Cloudblazer(final Cloudblazer card) {
        super(card);
    }

    @Override
    public Cloudblazer copy() {
        return new Cloudblazer(this);
    }
}
