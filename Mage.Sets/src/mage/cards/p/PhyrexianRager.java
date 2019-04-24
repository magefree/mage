

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PhyrexianRager extends CardImpl {

    public PhyrexianRager (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);
    }

    public PhyrexianRager (final PhyrexianRager card) {
        super(card);
    }

    @Override
    public PhyrexianRager copy() {
        return new PhyrexianRager(this);
    }

}
