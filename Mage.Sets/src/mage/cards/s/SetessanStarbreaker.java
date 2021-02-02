
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SetessanStarbreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public SetessanStarbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Setessa Starbreaker enters the battlefield, you may destroy target Aura.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SetessanStarbreaker(final SetessanStarbreaker card) {
        super(card);
    }

    @Override
    public SetessanStarbreaker copy() {
        return new SetessanStarbreaker(this);
    }
}
