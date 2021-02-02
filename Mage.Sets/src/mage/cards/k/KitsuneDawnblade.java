
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class KitsuneDawnblade extends CardImpl {

    public KitsuneDawnblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new BushidoAbility(1));
        // When Kitsune Dawnblade enters the battlefield, you may tap target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KitsuneDawnblade(final KitsuneDawnblade card) {
        super(card);
    }

    @Override
    public KitsuneDawnblade copy() {
        return new KitsuneDawnblade(this);
    }
}
