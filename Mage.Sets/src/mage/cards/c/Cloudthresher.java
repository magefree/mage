
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class Cloudthresher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Cloudthresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
        // When Cloudthresher enters the battlefield, it deals 2 damage to each creature with flying and each player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageAllEffect(2, "it", filter));
        ability.addEffect(new DamagePlayersEffect(2).setText("and each player"));
        this.addAbility(ability);
        // Evoke {2}{G}{G}
        this.addAbility(new EvokeAbility("{2}{G}{G}"));
    }

    private Cloudthresher(final Cloudthresher card) {
        super(card);
    }

    @Override
    public Cloudthresher copy() {
        return new Cloudthresher(this);
    }
}
